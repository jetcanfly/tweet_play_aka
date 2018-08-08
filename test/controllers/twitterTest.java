package controllers;

import akka.testkit.javadsl.TestKit;
import play.libs.F.Either;
import play.libs.Json;
import play.mvc.WebSocket;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.hamcrest.core.IsInstanceOf;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalatest.junit.JUnitSuite;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.NotUsed;
import actor.UserParentActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.actor.AbstractActor;
import scala.concurrent.duration.Duration;

/**
 * 
 * This class is for testing function of this twitter searching application
 * 
 * @author Zhongxu Huang
 * @version 1.0
 */
public class twitterTest extends JUnitSuite {
	/**
	 * An actor created for testing
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	  public static class SomeActor extends AbstractActor {
		    ActorRef target = null;

		    /**
		     * Receives messages of this actor
		     * @return Received message
		     * @author Zhongxu Huang
		     * @version 2.0
		     */
		    @Override
		    public Receive createReceive() {
		      return receiveBuilder()
		        .match(UserParentActor.Create.class, create -> {
		        	Sink <JsonNode,?> sink=Sink.ignore();
		        	ObjectNode jsonNode=Json.newObject();
		        	jsonNode.put("Name", "123");
		        	Source<JsonNode, ?> source=Source.single(jsonNode);
		         Flow<JsonNode, JsonNode, NotUsed> flow=Flow.fromSinkAndSource(sink, source);
		          getSender().tell(flow, getSelf());
		        })
		        .build();
		    }
		  }
		  
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
		   * Tests twitter web socket
		   * @author Zhongxu Huang
		   * @version 2.0
		   */
	@Test
	public void twittertest(){
		new TestKit(system) {
			{
		      final Props props = Props.create(SomeActor.class);
		      final ActorRef subject = system.actorOf(props);

		      // can also use JavaTestKit “from the outside”
		      final TestKit probe = new TestKit(system);
		      Twitter twitter=new Twitter(subject, subject);
		      assertTrue(twitter.ws() instanceof WebSocket);
		      }
		    };
	}
	
	/**
	 * Tests web socket future
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void wsFutureTest() throws InterruptedException, ExecutionException{
		new TestKit(system) {
			{
		      final Props props = Props.create(SomeActor.class);
		      final ActorRef subject = system.actorOf(props);

		      // can also use JavaTestKit “from the outside”
		      final TestKit probe = new TestKit(system);
		      Twitter twitter=new Twitter(subject, subject);
		      Flow<JsonNode, JsonNode, NotUsed> flow=twitter.wsFutureFlow("123").toCompletableFuture().get();
		      assertTrue(flow instanceof Flow);
		      }
		    };
	}
	
	/**
	 * Tests the search for user timeline
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void user_timelineTest(){
		new TestKit(system) {
			{
		      final Props props = Props.create(SomeActor.class);
		      final ActorRef subject = system.actorOf(props);

		      // can also use JavaTestKit “from the outside”
		      final TestKit probe = new TestKit(system);
		      Twitter twitter=new Twitter(subject, subject);
		      assertTrue(twitter.user_timeline() instanceof WebSocket);
		      }
		    };
	}
	
	/**
	 * Tests exceptions possible in Twitter
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void exceptionTest(){
		new TestKit(system) {
			{
		      final Props props = Props.create(SomeActor.class);
		      final ActorRef subject = system.actorOf(props);

		      // can also use JavaTestKit “from the outside”
		      final TestKit probe = new TestKit(system);
		      Twitter twitter=new Twitter(subject, subject);
		      assertTrue(twitter.logException(new Throwable("error")) instanceof Either<?,?>);
		      }
		    };
	}
}
