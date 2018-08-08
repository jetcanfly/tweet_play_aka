package actor;

import actor.UserActor.Factory;
import akka.actor.ActorRef;
import akka.stream.Materializer;

/**
 * A class to build test user
 * @author Zhongxu Huang
 * @version 2.0
 */
public class Testuser extends UserParentActor {
	private final UserActor.Factory childFactory;
	private final String keyword;
	/**
	 * Constructs a test user
	 * @param childFactory factory to return UserActor
	 * @param keyword search keyword
	 * @author Zhongxu Huang
	 * @version 2.0
	 */
	public Testuser(Factory childFactory, String keyword) {
		super(childFactory, "123");
		 
		this.childFactory = childFactory;
		this.keyword = "123";
	}
}
