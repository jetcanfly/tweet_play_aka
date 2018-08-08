package actor;

import static akka.pattern.PatternsCS.ask;
import static org.junit.Assert.*;
import static play.inject.Bindings.bind;

import java.util.concurrent.CompletionStage;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalatest.junit.JUnitSuite;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import controllers.Twitter;
import controllers.twitterTest.SomeActor;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.WebSocket;
import play.test.Helpers;
import scala.concurrent.duration.Duration;
import services.TwitterService;
/**
 * A class to test User Actor
 * @author Zhongxu Huang
 * @version 2.0
 */
public class UserActorTest extends JUnitSuite{
//	public static class SomeActor extends AbstractActor {
//		 ActorRef target = null;
//
//		    @Override
//		    public Receive createReceive() {
//		      return receiveBuilder()
//		        .match(UserActor.class, ua -> {
//		        	CompletionStage<TwitterService> future = ask(twitterActor, new Messages.SearchTweets(keywords), timeout)
//		    				.thenApply(TwitterService.class::cast);
//		          getSender().tell(future, getSelf());
//		        })
//		        .build();
//		    }
//	}
	static ActorSystem system;
	
//	UserActor ua ;
	
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
	 * Tests creating receive messages
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testCreateReceive() {
//		new TestKit(system) {
//			{
//		      final Props props = Props.create(SomeActor.class);
//		      final ActorRef subject = system.actorOf(props);
////
////		      
////		      // can also use JavaTestKit “from the outside”
//		      final TestKit probe = new TestKit(system);
//		     
//				final TestActorRef<UserActor> ref = TestActorRef.create(system, props, "testC");
//				final UserActor actor = ref.underlyingActor();
//				
//				actor.createReceive();
//			
//		      }
//		    };
	}
	
	/**
	 * Tests adding tweets
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public void addTweetsTest() {
//		new TestKit(system) {
//			{
//		      final Props props = Props.create(SomeActor.class,ua);
//		      final ActorRef subject = system.actorOf(props);
//
//		      
//		      // can also use JavaTestKit “from the outside”
//		      final TestKit probe = new TestKit(system);
//		     
//				final TestActorRef<UserActor> ref = TestActorRef.create(system, props, "testC");
//				final UserActor actor = ref.underlyingActor();
//				
//				actor.addTweets(twitterService);
//			
//		      }
//		    };
//	}
	}

}
