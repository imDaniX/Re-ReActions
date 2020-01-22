package pro.reactions.flags;

import pro.reactions.ReActionsCore;
import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.PlaceholdersManager;

public final class StoredFlag {
	private static final FlagsManager flgManager = ReActionsCore.getFlags();
	private static final PlaceholdersManager phManager = ReActionsCore.getPlaceholders();
	private final Flag flag;
	private final String value;
	private final boolean invert;

	public StoredFlag(Flag flag, String value, boolean invert) {
		this.flag = flag;
		this.value = value;
		this.invert = invert;
	}

	public Flag getFlag() {
		return flag;
	}

	public boolean check(Context context) {
		return !invert && flag.check(context, phManager.parsePlaceholders(value, context));
	}

	public static StoredFlag valueOf(String line) {
		boolean invert;
		if(line.startsWith("!")) {
			invert = true;
			line = line.substring(1);
		} else
			invert = false;
		int index = line.indexOf('=');
		Flag flg = flgManager.getFlag(line.substring(0, index));
		if(flg == null) return null;
		return new StoredFlag(flg, line.substring(index + 1), invert);
	}
}
