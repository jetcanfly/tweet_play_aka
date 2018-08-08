package services;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;

import akka.parboiled2.support.OpTreeContext.Optional;
import model.User;
import model.User_Profile;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import play.server.Server;
import play.libs.ws.WSClient;
import play.routing.RoutingDsl;
import play.api.mvc.Session;
import play.libs.Json;
import play.libs.oauth.OAuth.RequestToken;
import static play.mvc.Results.*;

import play.mvc.Http;
import play.mvc.Result;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
/**
 * Testing implementation class of TwitterSearch
 * @author Zhongxu Huang
 * @version 2.0
 */
public class TwitterSearchImpTest {
	
	private static WSClient ws;
	private static Server server;
	private static TwitterSearchImp ts;
	private static TwitterSearchImp ts1;
	private static WSClient ws1;
	private static Server userServer;
	
	/**
	 * Tests search function of TwitterSearch
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testSearch() throws InterruptedException, ExecutionException, IOException {
		ObjectNode value = Json.newObject();

		value.put("name", "Mingzhou Lin");
		value.put("screen_name", "Mingzhou_Lin");

		server = Server.forRouter(
				(components) -> RoutingDsl.fromComponents(components).GET("/q=%23somekeyword&count=100&result_type=recent").routeTo(()->{
					return ok(value);
				}).build());
		ws = play.test.WSTestClient.newClient(server.httpPort());
		ts=new TwitterSearchImp(ws);
		JsonNode jsonNode=ts.search("somekeyword", "/", "100").toCompletableFuture().get();
		assertEquals("Mingzhou_Lin", jsonNode.findPath("screen_name").asText());
		ws.close();
		server.stop();
	}

	/**
	 * Tests user finding function of TwitterSearch
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testFindUser() throws InterruptedException, ExecutionException, IOException {
		ObjectNode value = Json.newObject();
		value.put("name", "Mingzhou Lin");
		value.put("screen_name", "Mingzhou_Lin");
		userServer = Server.forRouter((components) -> RoutingDsl.fromComponents(components)
				.GET("/screen_name=screen_name&count=100").routeTo(()->{
					return ok(value);
				}).build());
		ws1 = play.test.WSTestClient.newClient(userServer.httpPort());
		ts=new TwitterSearchImp(ws1);
		JsonNode jsonNode=ts.findUser("screen_name", "/", "100").toCompletableFuture().get();
		assertEquals("Mingzhou_Lin", jsonNode.findPath("screen_name").asText());
		ws1.close();
		userServer.stop();
	}

	
}
