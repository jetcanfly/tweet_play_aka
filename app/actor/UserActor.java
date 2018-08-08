package actor;

import static akka.pattern.PatternsCS.ask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.assistedinject.Assisted;
import model.User;
import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Pair;
import akka.stream.KillSwitches;
import akka.stream.Materializer;
import akka.stream.UniqueKillSwitch;
import akka.stream.javadsl.BroadcastHub;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.MergeHub;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.Timeout;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import services.TwitterService;

/**
 * this class shows reaction of user actors when receiving message
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class UserActor extends AbstractActor {
	private final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

	// private final Map<String, UniqueKillSwitch> stocksMap = new HashMap<>();
	private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	private final String id;
	private final ActorRef twitterActor;
	private final Materializer mat;

	private final Sink<JsonNode, NotUsed> hubSink;
	private final Flow<JsonNode, JsonNode, NotUsed> websocketFlow;

	/**
	 * constructor of user actor with certain parameters
	 * 
	 * @param id
	 *            id of this user actor
	 * @param TwitterActor
	 *            this useractor's parent twitter actor
	 * @param mat
	 *            this actor's implementation of materializer service provider
	 *            interface
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Inject
	public UserActor(@Assisted String id, @Named("twitterActor") ActorRef TwitterActor, Materializer mat) {
		this.id = id;
		this.twitterActor = TwitterActor;
		this.mat = mat;

		Pair<Sink<JsonNode, NotUsed>, Source<JsonNode, NotUsed>> sinkSourcePair = MergeHub.of(JsonNode.class, 16)
				.toMat(BroadcastHub.of(JsonNode.class, 256), Keep.both()).run(mat);

		this.hubSink = sinkSourcePair.first();
		Source<JsonNode, NotUsed> hubSource = sinkSourcePair.second();

		Sink<JsonNode, CompletionStage<Done>> jsonSink = Sink.foreach((JsonNode json) -> {
			// When the user types in a stock in the upper right corner, this is triggered,
			String keyword = json.findPath("keyword").asText();
			search(keyword);
		}); // If client websocket send a message, it will reach here, add a new searching
			// service.
		// Sink<JsonNode, CompletionStage<Done>> jsonSink = Sink.ignore(); If clent
		// won't send message, use this.

		// Put the source and sink together to make a flow of hub source as output
		// (aggregating all
		// stocks as JSON to the browser) and the actor as the sink (receiving any JSON
		// messages
		// from the browse), using a coupled sink and source.
		// this.websocketFlow = Flow.fromSinkAndSourceCoupled(jsonSink, hubSource)
		this.websocketFlow = Flow.fromSinkAndSourceCoupled(jsonSink, hubSource)
				// .log("actorWebsocketFlow", logger)
				.watchTermination((n, stage) -> {
					// When the flow shuts down, make sure this actor also stops.
					stage.thenAccept(f -> context().stop(self()));
					return NotUsed.getInstance();
				});
	}

	/**
	 * Defines twitter user profile showing messages actor can handle along with the
	 * implementation of processing.
	 * 
	 * @return fallback of twitter search message
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Messages.SearchTweets.class, searchTweets -> {
			logger.info("Received message {}", searchTweets);
			if (searchTweets.keyword != "") {
				search(searchTweets.keyword);
			}
			sender().tell(websocketFlow, self());
		}).build();

	}

	/**
	 * search tweets with keyword by asking the twitter actor.
	 * 
	 * @param keywords
	 *            word contained by the aiming tweets
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	private void search(String keywords) {
		// Ask the stocksActor for a stream containing these stocks.
		CompletionStage<TwitterService> future = ask(twitterActor, new Messages.SearchTweets(keywords), timeout)
				.thenApply(TwitterService.class::cast);

		// when we get the response back, we want to turn that into a flow by creating a
		// single
		// source and a single sink, so we merge all of the stock sources together into
		// one by
		// pointing them to the hubSink, so we can add them dynamically even after the
		// flow
		// has started.
		future.thenAccept((TwitterService twitterService) -> {
			addTweets(twitterService);
		});
	}

	/**
	 * Adds a single tweet by using twitter service
	 * 
	 * @param twitterService
	 *            twitter related service of this application
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void addTweets(TwitterService twitterService) {
		logger.info("Adding tweets {}", twitterService);

		// We convert everything to JsValue so we get a single stream for the websocket.
		// Make sure the history gets written out before the updates for this stock...
		final CompletionStage<Source<JsonNode, NotUsed>> historySource = twitterService.result()
				.thenApply(source -> source.map(Json::toJson));
		final CompletionStage<Source<JsonNode, NotUsed>> updateSource = twitterService.update()
				.thenApply(source -> source.map(Json::toJson));
		final CompletionStage<Source<JsonNode, NotUsed>> tweetsSource = historySource.thenCombine(updateSource,
				(source1, source2) -> source1.concat(source2));

		// Set up a flow that will let us pull out a killswitch for this specific stock,
		// and automatic cleanup for very slow subscribers (where the browser has
		// crashed, etc).
		final Flow<JsonNode, JsonNode, UniqueKillSwitch> killswitchFlow = Flow.of(JsonNode.class)
				.joinMat(KillSwitches.singleBidi(), Keep.right());
		// Set up a complete runnable graph from the stock source to the hub's sink
		String name = "tweet-" + "-" + id;
		// final CompletionStage<RunnableGraph<UniqueKillSwitch>> graph = tweetsSource
		final CompletionStage<RunnableGraph<UniqueKillSwitch>> graph = tweetsSource
				.thenApply(source -> source.viaMat(killswitchFlow, Keep.right()).to(hubSink).named(name));

		// Start it up!
		CompletionStage<UniqueKillSwitch> killSwitch = graph.thenApply(graph1 -> graph1.run(mat));
		// User user_t = new User();
		// user_t.setName("test");
		// user_t.setScreen_name("test_screen");
		// user_t.setText("This is a test");
		// JsonNode temp = Json.toJson(user_t);
		// Source.single(temp).runWith(hubSink, mat); this is broadcast. Just give
		// producers to sink.
	}

	/**
	 * factory interface for creating child actor with certain id
	 * 
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public interface Factory {
		Actor create(String id);
	}

	/**
	 * tests always return true
	 * 
	 * @return true
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public boolean testMe() {
		return true;
	}
}
