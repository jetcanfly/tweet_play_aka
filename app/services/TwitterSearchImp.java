package services;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.oauth.OAuth.ConsumerKey;
import play.libs.oauth.OAuth.OAuthCalculator;
import play.libs.oauth.OAuth.RequestToken;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

/**
 * implementation of twitter searching and user profile listing
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class TwitterSearchImp implements TwitterSearch {

	private final WSClient ws;

	/**
	 * constructs object for twitter search with dependency injection
	 * 
	 * @param ws
	 *            web client for dependency injection
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Inject
	public TwitterSearchImp(WSClient ws) {
		this.ws = ws;
	}

	static final ConsumerKey KEY = new ConsumerKey("2iroUaMhDY0gvM3L3YSbxW6hh",
			"vWjxzgzCCs82AxKjpgFZLjPqbBs56cguim7Fqwly7OtAnHbo17");
	static final RequestToken tokenPair = new RequestToken("972498867395727362-ZnHppJDo3uOPTnHbeoFQs2251oHm8zD",
			"yudEuoghQ9OrvyBvkiBE6TXr616GprfNwaPjVka06UiyA");

	/**
	 * searches tweets containing certain keyword
	 * 
	 * @param keyword
	 *            word used for searching
	 * @param searchApi
	 *            string formed twitter API name used for searching tweets
	 * @param count
	 *            string formed number of tweets for searching result
	 * @return CompletionStage Result of latest numbers of tweets containing the
	 *         keyword.
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public CompletionStage<JsonNode> search(String keyword, String searchApi, String count) {
//		if (tokenPair != null) {
			String keywords = "q=%23" + keyword + "&count=" + count + "&result_type=recent";
			return ws.url(searchApi + keywords).sign(new OAuthCalculator(TwitterSearchImp.KEY, tokenPair)).get()
					.thenApply((WSResponse result) -> {
						return result.asJson();
					});
//		} else {
//			return null;
//		}
	}

	/**
	 * gets user profile of twitter user with this specific screen name
	 * 
	 * @param screen_name
	 *            screen name for twitter user
	 * 
	 * @param searchApi
	 *            string formed twitter API name used for searching twitter user
	 * @param count count
	 * @return this user's profile CompletionStage Result
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public CompletionStage<JsonNode> findUser(String screen_name, String searchApi, String count) {
//		if (tokenPair != null) {
			String keywords = "screen_name=" + screen_name + "&count=100";
			return ws.url(searchApi + keywords).sign(new OAuthCalculator(TwitterSearchImp.KEY, tokenPair)).get()
					.thenApply((WSResponse result) -> {
						return result.asJson();
					});
//		} else {
//			return null;
//		}
	}
}
