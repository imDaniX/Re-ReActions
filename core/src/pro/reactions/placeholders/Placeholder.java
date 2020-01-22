package pro.reactions.placeholders;

import pro.reactions.activators.storages.Context;

@FunctionalInterface
public interface Placeholder {
	/**
	 * Process this placeholder
	 * @param ph Placeholder itself(e.g. %var:test% - var) in lower case
	 * @param text Text of placeholder(e.g. %var:test% - test) in lower case
	 * @param context Context of activation
	 * @return Processed placeholder
	 */
	String processPlaceholder(String ph, String text, Context context);

	interface Equality extends Placeholder {
		/**
		 * Get all the ids for this placeholder
		 * @return Ids of placeholder
		 */
		String getId();
	}

	interface Prefixed extends Placeholder {
		/**
		 * Get all the prefixes for this placeholder
		 * @return Prefixes of placeholder
		 */
		String getPrefix();
	}

	interface Custom extends Placeholder {
		/**
		 * Get handler to register (if it wasn't registered yet)
		 * @return Parser of placeholder
		 */
		Parser getParser();
	}
}