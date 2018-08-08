package actor;

import static java.util.Objects.requireNonNull;

import java.util.List;

import actor.Messages.SearchTweets;
import model.User;

/**
 * this class shows format of messages for actors used by this twitter
 * application
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public final class Messages {
	/**
	 * defines message for doing twitter searching
	 * 
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public static final class SearchTweets {
		String keyword;

		/**
		 * constructor for tweetsearching messages
		 * 
		 * @param keyword
		 *            string used for filtering tweets
		 * 
		 * @author Mingzhou Lin
		 * @version 1.0
		 */
		public SearchTweets(String keyword) {
			this.keyword = requireNonNull(keyword);
		}

	}

	/**
	 * defines message for showing certain twitter user
	 * 
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public static final class SearchProfile {
		String screen_name;

		/**
		 * constructor for showing user profile messages
		 * 
		 * @param screen_name
		 *            twitter user's screen name
		 * @author Mingzhou Lin
		 * @version 1.0
		 */
		public SearchProfile(String screen_name) {
			this.screen_name = requireNonNull(screen_name);
		}

	}

	/**
	 * defines message for list of certain twitter user
	 * 
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public static class Users {
		public List<User> users;

		/**
		 * constructor for messages with list of twitter users
		 * 
		 * @param users
		 *            list of twitter users
		 * @author Mingzhou Lin
		 * @version 1.0
		 */
		public Users(List<User> users) {
			super();
			this.users = users;
		}
	}

}
