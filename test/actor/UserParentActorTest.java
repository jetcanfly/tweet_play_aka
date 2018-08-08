package actor;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;

import javax.inject.Inject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalatest.junit.JUnitSuite;

import actor.UserParentActor.Create;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.Helpers;
import services.TwitterSearch;
import services.twitterTestAPI;
/**
 * A class to test the UserParentActorTest
 * @author Zhongxu Huang
 * @version 2.0
 */
public class UserParentActorTest extends JUnitSuite{

	static ActorSystem system;
	private static Application testApp;
	 UserParentActor upa ;
	 
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
		testApp = new GuiceApplicationBuilder()
				.overrides(bind(UserParentActor.class).to(Testuser.class)).build();
		 UserParentActor upa = testApp.injector().instanceOf(UserParentActor.class);
		 
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
	 * Tests Create class by call constructor
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testcreate() {
		Create creat = new Create("trump");
	}

	/**
	 * Tests creating receive messages
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public void creatreceivetest() {
		final Props props = Props.create(UserParentActor.class,upa);
		final TestActorRef<UserParentActor> ref = TestActorRef.create(system, props, "testB");
		final UserParentActor actor = ref.underlyingActor();
	
		actor.createReceive();
		
	}

}
