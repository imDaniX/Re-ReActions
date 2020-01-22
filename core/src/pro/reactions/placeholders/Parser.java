package pro.reactions.placeholders;

import pro.reactions.activators.storages.Context;

public interface Parser {
	boolean add(Placeholder ph);
	String parse(String text, Context context);
	boolean isEmpty();
}
