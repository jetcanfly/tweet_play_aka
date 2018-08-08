package actor;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalatest.junit.JUnitSuite;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import controllers.Twitter;
import model.User_Profile;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.server.Server;

/**
 * A class testing UserProfileActorTest
 * @author Zhongxu Huang
 * @version 2.0
 */
public class UserProfileActorTest extends JUnitSuite {
	static ActorSystem system;

	/**
	 * Setups before the whole testing
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	/**
	 * Frees resources after the whole testing
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	/**
	 * Tests parsing User to object
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testParseUser() {
		final Props props = Props.create(UserProfileActor.class);
		final TestActorRef<UserProfileActor> ref = TestActorRef.create(system, props, "testA");
		final UserProfileActor actor = ref.underlyingActor();

		ObjectNode answer = Json.newObject();
		ObjectNode value2 = Json.newObject();

		answer.set("user", value2);
		answer.put("text", "Nice day!");
		value2.put("name", "Zhongxu Huang");
		value2.put("description", "Born 330 Live 310");
		value2.put("location", "LA, CA");
		value2.put("url", "http://bullcityrecords.com/wnng/");
		value2.put("id_str", "250075927172759552");

		User_Profile up = actor.ParseUser(answer);

		assertEquals("Zhongxu Huang", up.getName());
		assertEquals("Born 330 Live 310", up.getDescription());
		assertEquals("250075927172759552", up.getId_str());
		assertEquals("LA, CA", up.getLocation());
		
		assertEquals(false, up.getTexts().contains("Nice day!"));
		assertEquals("http://bullcityrecords.com/wnng/", up.getUrl());
	}

}
