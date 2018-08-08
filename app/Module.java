import com.google.inject.AbstractModule;

import actor.TwitterActor;
import actor.UserActor;
import actor.UserParentActor;
import actor.UserProfileActor;
import play.libs.akka.AkkaGuiceSupport;
import services.TwitterSearch;
import services.TwitterSearchImp;

/**
 * moudles of this twitter application
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class Module extends AbstractModule implements AkkaGuiceSupport {
	@Override
	/**
	 * configure actors and twitter searching service to its implementation
	 */
	public void configure() {
		bind(TwitterSearch.class).to(TwitterSearchImp.class);
		bindActor(TwitterActor.class, "twitterActor");
		bindActor(UserParentActor.class, "userParentActor");
		bindActor(UserProfileActor.class, "userProfileActor");
		bindActorFactory(UserActor.class, UserActor.Factory.class);
	}

}
