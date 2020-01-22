package pro.reactions.platform;

import java.net.InetAddress;
import java.util.UUID;

/**
 * Represents some online player
 * Only basic information is used
 */
public interface RaPlayer extends RaUser {

	/**
	 * Get player's unique user identification
	 * @return Player's UUID
	 */
	UUID getUniqueId();

	/**
	 * Get player's location
	 * @return Player's location
	 */
	RaLocation getLocation();

	/**
	 *
	 * @return
	 */
	InetAddress getIp();

	/**
	 *
	 * @param message
	 */
	void useChat(String message);
}
