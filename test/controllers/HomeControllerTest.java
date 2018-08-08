package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

/**
 * 
 * This class is for testing an action to handle HTTP requests to the
 * application's home page.
 * 
 * @author Zhongxu Huang
 * @version 1.0
 */
public class HomeControllerTest extends WithApplication {
	/**
	 * tests with this twitter searching Application created by Guice
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder().build();
	}

	/**
	 * 
	 * tests HomeController action through routing
	 * 
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testIndex() {
//		Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/");
//
//		Result result = route(app, request);
//		assertEquals(OK, result.status());
	}

}
