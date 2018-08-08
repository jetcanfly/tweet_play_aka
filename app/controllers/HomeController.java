package controllers;

import javax.inject.Inject;
import javax.inject.Named;
import model.User_Profile;
import org.webjars.play.WebJarsUtil;

import akka.actor.ActorRef;
import model.User;
import play.mvc.*;

/**
 * 
 * This controller contains an action to handle HTTP requests to the
 * application's home page and user profile page.
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class HomeController extends Controller {

	private WebJarsUtil webJarsUtil;

	/**
	 * constructs home controller for this twitter application
	 * 
	 * @param webJarsUtil
	 *            webjar utiliy for this twitter application
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Inject
	public HomeController(WebJarsUtil webJarsUtil) {
		this.webJarsUtil = webJarsUtil;
	}

	/**
	 * An action that renders an HTML page with a tweet search message.
	 * 
	 * @return rendered HTML page for searching tweets
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public Result index() {

		return ok(views.html.index.render(request(), webJarsUtil));
	}

	/**
	 * An action that renders an HTML page with a tweet user profile.
	 * 
	 * @param userName
	 *            twitter user's name
	 * @return endered HTML page for searching twitter user profile
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public Result index_userProfile(String userName) {
		User_Profile.userName = userName;
		return ok(views.html.userProfile.render(request(), webJarsUtil));
	}
}
