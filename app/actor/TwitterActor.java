package actor;

import javax.inject.Inject;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import services.TwitterSearch;
import services.TwitterService;
import akka.actor.AbstractActor.Receive;

public class TwitterActor extends AbstractActor {

	String searchAPI = "https://api.twitter.com/1.1/search/tweets.json?";

	@Inject
	private TwitterSearch twitterSearch;

	/**
	 * Defines search twitter messages actor can handle along with the
	 * implementation of processing.
	 * 
	 * @return fallback of twitter search message
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Messages.SearchTweets.class, searchTweets -> {
			TwitterService twitterService = new TwitterService(searchTweets.keyword, searchAPI, twitterSearch);
			sender().tell(twitterService, self());
		}).build();
	}

	/**
	 * tests always true
	 * 
	 * @return true
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public boolean testMe() {
		return true;
	}
}
