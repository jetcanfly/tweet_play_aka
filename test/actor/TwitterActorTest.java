package actor;

import org.scalatest.junit.JUnitSuite;

import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import akka.util.Timeout;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.Helpers;

import static akka.pattern.PatternsCS.ask;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.inject.Bindings.bind;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor;
import scala.concurrent.duration.Duration;
import services.TwitterSearch;
import services.TwitterService;
import services.twitterTestAPI;

/**
 * A testing class to test Twitter Actor
 * @author Zhongxu Huang
 * @version 2.0
 */
public class TwitterActorTest extends JUnitSuite {
	static ActorSystem system;
	private static Application testApp;
	private static TwitterService twitterService;
	
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
	 * Initializes testing application before test
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public static void initTestApp() {
		testApp = new GuiceApplicationBuilder().overrides(bind(TwitterSearch.class).to(twitterTestAPI.class)).build();
		TwitterSearch testTwitter = testApp.injector().instanceOf(TwitterSearch.class);
		
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
	 * Stops applications after the whole testing
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public static void stopTestApp() {
		Helpers.stop(testApp);
	}

	/**
	 * Tests creating receive messages
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void creatreceivetest() {

		final Props props = Props.create(TwitterActor.class);
		final TestActorRef<TwitterActor> ref = TestActorRef.create(system, props, "testA");
		final TwitterActor actor = ref.underlyingActor();

		
		actor.createReceive();
		assertNotNull(ref.actorContext());
		//ref.tell(new Messages.SearchTweets("123"), actor.self());
		
		assertTrue(actor.testMe());
	}

}
