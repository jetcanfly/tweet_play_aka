package services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;

import play.libs.Json;

/**
 * A twiiter API used in testing
 * @author Zhongxu Huang
 * @verison 2.0
 */
public class twitterTestAPI implements TwitterSearch{
	/**
	 * Tests search function in Twitter API
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public CompletionStage<JsonNode> search(String keyword, String searchApi, String count){
		ObjectNode jsonObject=Json.newObject();
		jsonObject.put(keyword,keyword);
		jsonObject.put(searchApi,searchApi);
		jsonObject.put(count, count);
		CompletionStage<JsonNode> json=CompletableFuture.supplyAsync(
                ()->jsonObject);
		return json;
	};

	/**
	 * Tests finding user function in Twitter API
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public CompletionStage<JsonNode> findUser(String screen_name, String searchApi, String count){
		ObjectNode jsonObject=Json.newObject();
		jsonObject.put(searchApi,searchApi);
		jsonObject.put(count, count);
		CompletionStage<JsonNode> json=CompletableFuture.supplyAsync(
                ()->jsonObject);
		return json;
	};
}
