package model;

import java.util.ArrayList;

/**
 * this class shows required twitter user profile information
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class User_Profile
{
	String id_str;
	String name;
	String location;
	String description;
	String url;
	static public String userName = "";
	ArrayList<String> texts = new ArrayList<>();

	/**
	 * gets user ID in string
	 * 
	 * @return user ID in string
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getId_str()
	{
		return id_str;
	}

	/**
	 * sets user ID with this id_str
	 * 
	 * @param id_str
	 *            user ID string to be set
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setId_str(String id_str)
	{
		this.id_str = id_str;
	}

	/**
	 * gets user name string
	 * 
	 * @return user's name
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * sets user name with this string
	 * 
	 * @param name
	 *            user name to be set
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * gets location of this user's profile
	 * 
	 * @return this user's location
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * sets location of this user's profile
	 * 
	 * @param location
	 *            location string to be set
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/**
	 * gets description of this user
	 * 
	 * @return description of this user
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * sets description of this user
	 * 
	 * @param description
	 *            string descripts this user
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * gets url of this user's profile
	 * 
	 * @return url string of this user's profile
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * sets this user's url
	 * 
	 * @param url
	 *            url string for this user's profile
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * gets arraylist of this user's tweets
	 * 
	 * @return arraylist of this user's tweets
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public ArrayList<String> getTexts()
	{
		return texts;
	}

	/**
	 * adds tweet to this user's tweets arraylist
	 * 
	 * @param text
	 *            this user's tweets string to be added
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public void addTexts(String text)
	{
		texts.add(text);
	}

}
