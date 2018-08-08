package model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Test;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import java.util.ArrayList;
import model.User;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import java.util.Arrays;

import org.junit.Assert.*;

/**
 * This class is for testing class User_profile
 * 
 * @author Zhongxu Huang
 * @version 1.0
 */
public class User_ProfileTest {
	User_Profile user_profile = new User_Profile();
	User_Profile user_profile1 = new User_Profile();

	/**
	 * 
	 * tests setters and getters for fields in class User_Profile
	 * 
	 * @author Zhongxu Huang
	 * @version 1.0
	 */
	@Test
	public void test() {

		user_profile.name = "donald";
		user_profile.id_str = "donald1994";
		user_profile.location = "canada";
		user_profile.description = "kind";
		user_profile.url = "";
		user_profile.addTexts("niceday");
		assertEquals("donald", user_profile.getName());
		assertEquals("donald1994", user_profile.getId_str());
		assertEquals("canada", user_profile.getLocation());
		assertEquals("kind", user_profile.getDescription());
		assertEquals("", user_profile.getUrl());
		String text = user_profile.getTexts().get(0);
		assertEquals("niceday", text);
		user_profile1.setId_str("mingzhou123");
		assertEquals("mingzhou123", user_profile1.getId_str());
		user_profile1.setDescription("fly");
		assertEquals("fly", user_profile1.getDescription());
		user_profile1.setLocation("USA");
		assertEquals("USA", user_profile1.getLocation());
		user_profile1.setUrl("1");
		assertEquals("1", user_profile1.getUrl());
		user_profile1.setName("mingzhou");
		assertEquals("mingzhou", user_profile1.getName());

	}

}
