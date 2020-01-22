package pro.reactions.actions;

import pro.reactions.activators.storages.Context;

public interface Action {
	/**
	 * Execute action
	 * @param context Context of activation
	 * @param value Value of action
	 * @return Is action executed successfully
	 */
	boolean execute(Context context, String value);

	/**
	 * Get name of action to register
	 * @return Action's name
	 */
	String getName();

	/**
	 * Is this action can be executed in async thread
	 * If not - core will try to execute it in main thread
	 * @return Is action executable in async thread
	 */
	default boolean isAsync() {
		return true;
	}
}
