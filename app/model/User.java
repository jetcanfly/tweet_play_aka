package model;

import java.util.ArrayList;

/**
 * 
 * this class shows required twitter user information
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class User {
	String screen_name;
	String text;
	String name;
	String keyword;

	/**
	 * gets this user's name
	 * 
	 * @return string of this user's name
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * set this user's name
	 * 
	 * @param names
	 *            string of user name to be set
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setName(String names) {
		this.name = names;
	}

	/**
	 * gets this user's screen name given by twitter
	 * 
	 * @return this user's screen name
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getScreen_name() {
		return screen_name;
	}

	/**
	 * sets this user's screen name
	 * 
	 * @param screen_name
	 *            screen name of user to be set
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	/**
	 * gets this user's tweet content which containing keyword
	 * 
	 * @return this user's tweet content
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getText() {
		return text;
	}

	/**
	 * sets this user's tweet content
	 * 
	 * @param text
	 *            this tweet's content string to be set
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * gets the keyword in the tweet.
	 * 
	 * @return string of searching keyword that leads to this tweet
	 * @author Liran An
	 * @version 1.0
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * set the keyword in the tweet.
	 * 
	 * @param keyword
	 *            string of searching keyword that leads to this tweet
	 * @author Liran An
	 * @version 1.0
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
