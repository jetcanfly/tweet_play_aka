package controllers;

import play.api.mvc.SatisfiableRangeSet;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.F.Either;
import play.libs.oauth.OAuth;
import play.libs.oauth.OAuth.ConsumerKey;
import play.libs.oauth.OAuth.OAuthCalculator;
import play.libs.oauth.OAuth.RequestToken;
import play.libs.oauth.OAuth.ServiceInfo;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;
import services.TwitterSearch;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.base.Strings;
import model.User_Profile;
import actor.UserParentActor;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.stream.Attributes.Name;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.Timeout;
import model.User;
import model.User_Profile;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import static akka.pattern.PatternsCS.ask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import views.html.*;

/**
 * 
 * Controller for Tweet Analytics, contains action of filtering tweets with
 * keyword, forming twitter account information as well as user profile
 * information using web socket.
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class Twitter extends Controller {

	private final Timeout t = new Timeout(Duration.create(1, TimeUnit.SECONDS));

	private final Logger logger = org.slf4j.LoggerFactory.getLogger("controllers.HomeController");

	private ActorRef userParentActor;

	private ActorRef userProfileActor;

	public String userProfileAPI = "https://api.twitter.com/1.1/statuses/user_timeline.json?";

	/**
	 * constructs twitter object with certain parameters listed.
	 * 
	 * @param userParentActor
	 *            parent actor in this tweet operation
	 * @param userProfileActor
	 *            actor for showing user profile in this tweet operation
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Inject
	public Twitter(@Named("userParentActor") ActorRef userParentActor,
			@Named("userProfileActor") ActorRef userProfileActor) {
		this.userParentActor = userParentActor;
		this.userProfileActor = userProfileActor;
	}

	/**
	 * Handles webSockets to get completion stage of tweet operations using Akka
	 * streams.
	 * 
	 * @return webSocket that access to the request header and supply twitter
	 *         services
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public WebSocket ws() {
		return WebSocket.Json.acceptOrResult(request -> {
			final CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> future = wsFutureFlow("");
			final CompletionStage<Either<Result, Flow<JsonNode, JsonNode, ?>>> stage = future.thenApply(Either::Right);
			return stage.exceptionally(this::logException);
		});
	}

	/**
	 * triggers the searching of keyword using akka flow.
	 * 
	 * @param keyword
	 *            twitter's keyword for actor to search
	 * @return completion stage of akkaflow formed searching result
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> wsFutureFlow(String keyword) {
		// long id = request.asScala().id();
		UserParentActor.Create create = new UserParentActor.Create(keyword);

		return ask(userParentActor, create, t).thenApply((Object flow) -> {
			final Flow<JsonNode, JsonNode, NotUsed> f = (Flow<JsonNode, JsonNode, NotUsed>) flow;
			return f.named("websocket");
		});
	}

	/**
	 * logs the errors or exceptions happening and informs them to server.
	 * 
	 * @param throwable
	 *            errors or exceptions this method catches
	 * @return action result for errors and exceptions
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public Either<Result, Flow<JsonNode, JsonNode, ?>> logException(Throwable throwable) {
		logger.error("Cannot create websocket", throwable);
		Result result = Results.internalServerError("error");
		return Either.Left(result);
	}
	// /**
	// * queries tweets with specific keywords
	// *
	// * @param keyword
	// * keyword used to filter recent tweets
	// * @return CompletionStage Result of latest 10 tweets containing the keyword.
	// * @author Mingzhou Lin
	// * @version 1.0
	// */
	// public CompletionStage<Result> homeTimeline(String keyword) {
	// if (keyword == null || keyword.equals("keyword")){
	// DynamicForm requestData = formFactory.form().bindFromRequest();
	// if(requestData.get("keyword") != null)
	// keyword = requestData.get("keyword");
	// }
	//
	// String keywords = "q=%23" + keyword + "&count=100";
	//// Optional<RequestToken> sessionTokenPair = getSessionTokenPair();
	//// return ws.url(searchAPI + keywords).sign(new OAuthCalculator(Twitter.KEY,
	// sessionTokenPair.get())).get()
	//// .thenApply((WSResponse result) -> {
	//// return
	// ok(tweet_index.render(parseSearch(result.asJson().findPath("statuses"))));
	//// });
	// return twitter.search(keywords, searchAPI)
	// .thenApply(result->result==null?notFound("404"):ok(tweet_index.render(parseSearch(result.findPath("statuses")))));
	// }

	/**
	 * gets user profile of twitter user with this twitter user.
	 * 
	 * @return web socket for this user's profile
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public WebSocket user_timeline() {
		return WebSocket.Json.acceptOrResult(request -> {
			// CompletionStage<JsonNode> userProfile= ask(userProfileActor, "twitterAPI",
			// t).thenCompose((Object f)->{
			// CompletionStage<JsonNode> user=(CompletionStage<JsonNode>) f;
			// return user;
			// });
			// Sink<JsonNode, ?> in = Sink.ignore();
			// CompletionStage<Source<JsonNode,?>>
			// source=userProfile.thenApply(json->Source.single(json));
			// CompletionStage<Flow<JsonNode, JsonNode, NotUsed>>
			// flow=source.thenApply(s->Flow.fromSinkAndSource(in, s));
			String pattern = "/user/(.*)";
			// Pattern r = Pattern.compile(pattern);
			// Matcher m = r.matcher(request.header.target.path);
			// String keyword_userName;
			// if (m.find()){
			// keyword_userName = m.group(0);
			// }
			// else{
			// keyword_userName = "twitterAPI";
			// }

			CompletionStage<JsonNode> userProfile = ask(userProfileActor, User_Profile.userName, t)
					.thenCompose((Object f) -> {
						CompletionStage<JsonNode> user = (CompletionStage<JsonNode>) f;
						return user;
					});
			Sink<JsonNode, ?> in = Sink.ignore();
			CompletionStage<Source<JsonNode, ?>> source = userProfile.thenApply(json -> Source.single(json));
			CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> flow = source
					.thenApply(s -> Flow.fromSinkAndSource(in, s));
			final CompletionStage<Either<Result, Flow<JsonNode, JsonNode, ?>>> stage = flow.thenApply(Either::Right);
			return stage.exceptionally(this::logException);
		});
	}
//	/**
//	 * parses this JSonNode to user profile containing specific fields.
//	 * 
//	 * @param answer
//	 *            JsonNode to be parsed
//	 * @return Use profile with specific field for user profile page
//	 * @author Mingzhou Lin
//	 * @version 1.0
//	 */
	// public User_Profile ParseUser(JsonNode answer) {
	// User_Profile user_Profile = new User_Profile();
	// JsonNode user = answer.findPath("user");
	// user_Profile.setId_str(user.findPath("id_str").textValue());
	// user_Profile.setName(user.findPath("name").textValue());
	// user_Profile.setLocation(user.findPath("location").textValue());
	// user_Profile.setDescription(user.findPath("description").textValue());
	// user_Profile.setUrl(user.findPath("url").textValue());
	// int num = 0;
	// for (JsonNode jsonNode : answer) {
	// num++;
	// user_Profile.addTexts(jsonNode.findPath("text").textValue());
	// if (num == 10) {
	// break;
	// }
	// }
	// return user_Profile;
	// }

}