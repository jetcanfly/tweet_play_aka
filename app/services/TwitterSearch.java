package services;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.oauth.OAuth.ConsumerKey;
import play.libs.oauth.OAuth.RequestToken;

/**
 * interface for this twitter searching application
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public interface TwitterSearch {
	public CompletionStage<JsonNode> search(String keyword, String searchApi, String count);

	public CompletionStage<JsonNode> findUser(String screen_name, String searchApi, String count);
}
