package services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static play.inject.Bindings.bind;
import akka.NotUsed;
import akka.stream.javadsl.Source;
import model.InitUser;
import model.User;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.core.Build;
import play.libs.Json;
import play.test.Helpers;
import scala.collection.Seq;
/**
 * Tests functions of TwitterService class
 * @author Zhongxu Huang
 * @version 2.0
 */
public class TwitterServiceTest{
	
	private static TwitterService twitterService;
	
	private static Application testApp;
	
	/**
	 * Setups before the whole testing to initialize test application
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@BeforeClass
	public static void initTestApp() {
		testApp = new GuiceApplicationBuilder()
				.overrides(bind(TwitterSearch.class).to(twitterTestAPI.class)).build();
		TwitterSearch testTwitter = testApp.injector().instanceOf(TwitterSearch.class);
//		Application application = new GuiceApplicationBuilder()
//			    .bindings(new ComponentModule())
//			    .bindings(bind(TwitterSearch.class).to(TwitterSearchImp.class))
//			    .build();
		twitterService=new TwitterService("123", "/", testTwitter);
	}
	
	/**
	 * Stops test application after the whole testing
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@AfterClass
	public static void stopTestApp() {
	     Helpers.stop(testApp);
	   }

	/**
	 * Tests the result received
	 * @author Zhongxu Huang
	 * @verison 2.0
	 */
	@Test
	public void testresult() {
		
	}
	
	/**
	 * Tests initParseSearch function of TwitterService
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testinitParseSearch() {
		ObjectNode answer = Json.newObject();
		ObjectNode value2 = Json.newObject();
		ObjectNode repo1 = Json.newObject();
		answer.set("user", value2);
		answer.put("text", "Nice day!");
		value2.put("name", "Zhongxu Huang");
		value2.put("description", "Born 330 Live 310");
		value2.put("location", "LA, CA");
		value2.put("url", "http://bullcityrecords.com/wnng/");
		value2.put("id_str", "250075927172759552");
		repo1.set("statuses", answer);
		ArrayList<User> user = twitterService.initParseSearch(repo1);
		assertEquals(false, user.contains("Zhongxu Huang"));
		assertEquals(false, user.contains("Born 330 Live 310"));
		assertEquals(false, user.contains("LA, CA"));
		assertEquals(false, user.contains("Nice day!"));
		assertEquals(false, user.contains("http://bullcityrecords.com/wnng/"));
		assertEquals(false, user.contains("250075927172759552"));
	}
	
	/**
	 * Tests parseSearch functions of TwitterService
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void parseSearch() {
		User user1 = new User();
		user1.setName("Zhongxu Huang");
		user1.setScreen_name("donald");
		user1.setText("Nice day!");
		ObjectNode answer = Json.newObject();
		ObjectNode value2 = Json.newObject();
		ObjectNode repo1 = Json.newObject();

		answer.set("user", value2);
		answer.put("text", "Nice day!");
		value2.put("name", "Zhongxu Huang");
		value2.put("screen_name", "donald");
		value2.put("description", "Born 330 Live 310");
		value2.put("location", "LA, CA");
		value2.put("url", "http://bullcityrecords.com/wnng/");
		value2.put("id_str", "250075927172759552");
		repo1.set("statuses", answer);
		User user = twitterService.parseSearch(repo1);
		assertEquals(user1.getName(), user.getName());
		assertEquals(user1.getScreen_name(),user.getScreen_name());
		assertEquals(user1.getText(), user.getText());
		
		
	}
}
