package actor;

import com.fasterxml.jackson.databind.JsonNode;
import static akka.pattern.PatternsCS.ask;
import static akka.pattern.PatternsCS.pipe;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.typesafe.config.Config;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.AbstractActor.Receive;
import akka.util.Timeout;
import play.libs.akka.InjectedActorSupport;
import model.*;

/**
 * this class shows reaction of user parent actor when receiving message
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class UserParentActor extends AbstractActor implements InjectedActorSupport {

	private final Timeout timeout = new Timeout(2, TimeUnit.SECONDS);
	private final String keyword;

	/**
	 * defines message for creating child actor
	 * 
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public static class Create {
		final String id;

		/**
		 * constructor for creating child actor messages
		 * 
		 * @param id
		 *            id of new born child actor
		 * 
		 * @author Mingzhou Lin
		 * @version 1.0
		 */
		public Create(String id) {
			this.id = id;
		}
	}

	private final UserActor.Factory childFactory;

	/**
	 * constructor of user parent actor with certain parameters
	 * 
	 * @param childFactory
	 *            implementation of user actor factory
	 * @param keyword
	 *            keyword used for searching tweets
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Inject
	public UserParentActor(UserActor.Factory childFactory, String keyword) {
		this.childFactory = childFactory;
		this.keyword = keyword;
	}

	/**
	 * Defines creating child messages this actor can handle along with the
	 * implementation of processing.
	 * 
	 * @return fallback of create child message
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(UserParentActor.Create.class, create -> {
			ActorRef child = injectedChild(() -> childFactory.create(create.id), "userActor-" + create.id);
			CompletionStage<Object> future = ask(child, new Messages.SearchTweets(create.id), timeout);
			pipe(future, context().dispatcher()).to(sender());
		}).build();
	}

	/**
	 * tests always true
	 * 
	 * @return true
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	

}
