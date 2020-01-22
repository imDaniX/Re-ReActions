package pro.reactions.flags;

import pro.reactions.activators.storages.Context;

public interface Flag {
	boolean check(Context context, String value);

	String getName();
}
