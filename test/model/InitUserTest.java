package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * A class to testing initializing users
 * @author Zhongxu Huang
 * @version 2.0
 */
public class InitUserTest {
	User user = new User();
	User user1 = new User();

	/**
	 * Tests user initializations
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	@Test
	public void testInitUser() {
		user.name = "ls";
		user.screen_name = "ws";
		user.text = "everyday";
		user1.name = "ls";
		user1.screen_name = "ws";
		user1.text = "everyday";
		List<User> users = Arrays.asList(user, user1);
		List<User> users1 = Arrays.asList(user, user1);
		InitUser inituser = new InitUser(users);
		inituser.setUsers(users1);
		List newusers = inituser.getUsers();
		assertEquals(users1, newusers);
	}

}
