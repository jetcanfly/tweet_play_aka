import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.AbstractModule;


import akka.actor.ActorSystem;
import akka.actor.Props;

import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import play.libs.akka.AkkaGuiceSupport;
/**
 * A class to test the configuration module of Guice 
 * @author Zhongxu Huang
 * @version 2.0
 */
public  class ModuleTest{

	Module module =new Module();
	
	/**
	 * Tests the guice dependency inject configuratuon module
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testConfigure() {
//		module.configure();	
	}
	

}


