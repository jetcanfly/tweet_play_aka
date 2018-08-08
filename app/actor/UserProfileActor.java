package actor;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import services.TwitterSearch;
import akka.actor.AbstractActor.Receive;
import model.User_Profile;
import play.libs.Json;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * this class shows reaction of userprofile actors when receiving message
 * 
 * @author Mingzhou Lin
 * @version 1.0
 */
public class UserProfileActor extends AbstractActor {

	@Inject
	private TwitterSearch twitterSearch;

	public String userProfileAPI = "https://api.twitter.com/1.1/statuses/user_timeline.json?";

	/**
	 * Defines twitter user's profile messages actor can handle along with the
	 * implementation of processing.
	 * 
	 * @return fallback of userprofile message
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, screen_name -> {
			CompletionStage<JsonNode> profile = twitterSearch.findUser(screen_name, userProfileAPI, "10");
			CompletionStage<JsonNode> userProfile = profile.thenApply(info -> ParseUser(info)).thenApply(Json::toJson);
			sender().tell(userProfile, self());
		}).build();
	}

	/**
	 * parses this JSonNode to user profile containing specific fields.
	 * 
	 * @param answer
	 *            JsonNode to be parsed
	 * @return Use profile with specific field for user profile page
	 * @author Mingzhou Lin
	 * @version 1.0
	 */
	public User_Profile ParseUser(JsonNode answer) {
		User_Profile user_Profile = new User_Profile();
		JsonNode user = answer.findPath("user");
		user_Profile.setId_str(user.findPath("id_str").textValue());
		user_Profile.setName(user.findPath("name").textValue());
		user_Profile.setLocation(user.findPath("location").textValue());
		user_Profile.setDescription(user.findPath("description").textValue());
		user_Profile.setUrl(user.findPath("url").textValue());
		int num = 0;
		for (JsonNode jsonNode : answer) {
			num++;
			user_Profile.addTexts(jsonNode.findPath("text").textValue());
			if (num == 10) {
				break;
			}
		}
		return user_Profile;
	}

}
