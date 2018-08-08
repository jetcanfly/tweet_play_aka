package model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import org.junit.Assert.*;

/**
 * 
 * This class is for testing class User
 * 
 * @author Zhongxu Huang
 * @version 1.0
 */
public class UserTest {
	User user = new User();

	/**
	 * 
	 * tests setters and getters for fields in class User
	 * 
	 * @author Zhongxu Huang
	 * @version 1.0
	 */
	@Test
	public void test() {

		user.name = "ls";
		user.screen_name = "ws";
		user.text = "everyday";

		assertEquals("ls", user.getName());
		assertEquals("ws", user.getScreen_name());
		assertEquals("everyday", user.getText());
		user.setName("zs");
		assertEquals("zs", user.getName());
		user.setScreen_name("ww");
		assertEquals("ww", user.getScreen_name());
		user.setText("lol");
		assertEquals("lol", user.getText());

	}

}
