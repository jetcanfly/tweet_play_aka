package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * this class shows required list of user's information
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class InitUser {
	List<User> users = new ArrayList<>();

	/**
	 * gets this inituser's user list
	 * 
	 * @return this object's users list
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * constructs this inituser with list of users
	 * 
	 * @param users
	 *            list of users
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public InitUser(List<User> users) {
		super();
		this.users = users;
	}

	/**
	 * sets this inituser's user list
	 * 
	 * @param users
	 *            list of users to be set
	 * @author ingzhou Lin
	 * @version 1.0
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
