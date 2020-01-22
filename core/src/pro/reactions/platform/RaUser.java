package pro.reactions.platform;

import pro.reactions.util.Util;

/**
 * Represents something, that can have permissions and interact with commands
 * Only basic information is used
 */
public interface RaUser {
	/**
	 * Get base user
	 * @return Base user
	 */
	Object getObject();

	/**
	 * Get user's name
	 * @return User's name
	 */
	String getName();

	/**
	 * Check permission
	 * @param perm Permission to check
	 * @return Does user has this permission
	 */
	boolean hasPermission(String perm);

	/**
	 * Send message to user
	 * @param message Message to send
	 */
	void sendMessage(String message);

	/**
	 *
	 * @param command
	 * @return
	 */
	boolean executeCommand(String command);

	/**
	 * Is user equals to console
	 * @return Is console
	 */
	boolean isConsole();

	/**
	 * Safely get base user
	 * @param clazz User's class
	 * @param <T> Type of user
	 * @return Base user or null
	 */
	default <T> T getBase(Class<T> clazz) {
		return Util.safeCast(clazz, getObject());
	}
}
