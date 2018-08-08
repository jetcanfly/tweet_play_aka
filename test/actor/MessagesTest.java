package actor;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import actor.Messages.SearchProfile;
import actor.Messages.SearchTweets;
import actor.Messages.Users;
import model.User;
/**
 * A class to test messages
 * @author Zhongxu Huang
 * @version 2.0
 */
public class MessagesTest {
	Messages messages = new Messages();
	User user = new User();
	User user1 = new User();
	List<User> userss = Arrays.asList(user, user1);

	/**
	 * Generates testing messages
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void test() {
		SearchTweets searchTweets =   new SearchTweets("trump");
		  searchTweets.keyword = "trump";
		  SearchProfile searchPrpfile = new SearchProfile("trump123");
		  searchPrpfile.screen_name = "trump123";
		  Users users = new Users(userss);
	}

}
