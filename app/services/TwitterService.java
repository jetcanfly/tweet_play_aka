package services;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import akka.NotUsed;
import akka.japi.Pair;
import akka.japi.function.Function;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Source;
import model.InitUser;
import model.User;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import com.sun.prism.impl.Disposer.Record;
import akka.stream.impl.fusing.Map;

/**
 * this class defines twitter related service including search keyword and show
 * twitter users
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class TwitterService {
	public String  keyword;

	private final CompletionStage<Source<JsonNode, NotUsed>> initial;
	private final CompletionStage<Source<JsonNode, NotUsed>> addition;
	private static java.util.Map<String, User> record = new HashMap<>();

	private static final FiniteDuration duration = Duration.create(5, TimeUnit.SECONDS);

	/**
	 * constructs a twitter service with listed parameters
	 * 
	 * @param keyword
	 *            word used for searching
	 * @param searchApi
	 *            string formed twitter API name used for searching tweets
	 * @param twitterSearch
	 *            object supports twitter searching and user profile listing
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Inject
	public TwitterService(String keyword, String searchApi, TwitterSearch twitterSearch) {
		this.keyword = keyword;
		CompletionStage<JsonNode> searchResult = twitterSearch.search(keyword, searchApi, "1");
		// CompletionStage<JsonNode> initiation=twitterSearch.search(keyword,
		// searchApi,"10");
		CompletionStage<JsonNode> initiation = twitterSearch.search(keyword, searchApi, "50");
		initial = initiation.thenApply(init -> Source.single(init));
		addition = searchResult.thenApply(result -> Source.unfoldAsync(result,
				(Function<JsonNode, CompletionStage<Optional<Pair<JsonNode, JsonNode>>>>) last -> {
					CompletionStage<JsonNode> next = twitterSearch.search(keyword, searchApi, "1");
					return next.thenApply(result1 -> Optional.of(Pair.apply(result1, result1)));
				}));
		// source=Source.unfold(, (Function<CompletionStage<JsonNode>,
		// Optional<Pair<CompletionStage<JsonNode>, CompletionStage<JsonNode>>>>) last
		// -> {
		// CompletionStage<JsonNode> next = twitterSearch.search(keyword, searchApi);
		// return Optional.of(Pair.apply(next, next));
		// });
	}

	/**
	 * builds a source of list of twitter user
	 * 
	 * @return a source of list of twitter user
	 * @author Mingzhou Lin
	 * @version 1.0
	 */

	// public CompletionStage<Source<InitUser, NotUsed>> result() {
	// return initial.thenApply(source->source.map(init->new
	// InitUser(initParseSearch(init))));
	// }
	public CompletionStage<Source<User, NotUsed>> result() {
		// return initial.thenApply(source->source.map(init->new
		// InitUser(initParseSearch(init)).mapConcat(userlist -> userlist))
		return initial.thenApply(source -> source.map(init -> initParseSearch(init)).mapConcat(userlist -> userlist));
	}

	/**
	 * updates a source of twitter user
	 * 
	 * @return a source of twitter user
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public CompletionStage<Source<User, NotUsed>> update() {
		return addition.thenApply(source -> source.throttle(1, duration, 1, ThrottleMode.shaping()).map(answer -> {
			return parseSearch(answer);
		}));
	}

	/**
	 * parses this JSonNode to arraylist of User.
	 * 
	 * @param answer
	 *            JsonNode to be parsed
	 * @return arraylist including specific fields of one twitter user
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public ArrayList<User> initParseSearch(JsonNode answer) {
		JsonNode body = answer.findPath("statuses");

		ArrayList<User> newUser = new ArrayList<>();
		int num = 0;
		// for (JsonNode jsonNode : answer) {
		for (JsonNode jsonNode : body) {
			num++;
			JsonNode info = jsonNode.findPath("user");
			User user = new User();
			user.setName(info.findPath("name").textValue());
			user.setScreen_name(info.findPath("screen_name").textValue());
			user.setText(jsonNode.findPath("text").textValue());
			user.setKeyword(keyword);
			newUser.add(user);
			if (num == 10) {
				break;
			}
		}
		return newUser;
	}

	/**
	 * parses this JSonNode to twitter User.
	 * 
	 * @param answer
	 *            JsonNode to b e parsed
	 * @return twitter user represented by this jsonNode
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public User parseSearch(JsonNode answer) {
		JsonNode body = answer.findPath("statuses");
		JsonNode info = body.findPath("user");
		User user = new User();
		user.setName(info.findPath("name").textValue());
		user.setScreen_name(info.findPath("screen_name").textValue());
		user.setText(body.findPath("text").textValue());
		user.setKeyword(keyword);
		if (record.containsKey(user.getName() + user.getText())) {
			return new User();
		} else {
			record.put(user.getName() + user.getText(), user);
			return user;
		}
	}

}
