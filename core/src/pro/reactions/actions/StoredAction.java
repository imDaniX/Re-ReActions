package pro.reactions.actions;

import pro.reactions.ReActionsCore;
import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.PlaceholdersManager;
import pro.reactions.util.Util;

public final class StoredAction {
	private static final ActionsManager actManager = ReActionsCore.INSTANCE.getActions();
	private static final PlaceholdersManager phManager = ReActionsCore.INSTANCE.getPlaceholders();
	private final Action action;
	private final String value;
	private final boolean precompiled;

	public StoredAction(Action action, String value, boolean precompiled) {
		this.action = action;
		this.value = value;
		this.precompiled = precompiled;
	}

	public Action getAction() {
		return action;
	}

	public boolean execute(Context context) {
		return action.execute(context, precompiled ? value : phManager.parsePlaceholders(value, context));
	}

	public static StoredAction fromString(String line) {
		int index = line.indexOf('=');
		String actStr = Util.removeSpaces(line.substring(0, index));
		String value = line.substring(index + 1);
		boolean precompile;
		if(actStr.startsWith("#")) {
			actStr = actStr.substring(1);
			precompile = true;
		} else precompile = !value.contains("%");
		Action act = actManager.getAction(actStr);
		if(act == null) return null;
		if(precompile && act instanceof PrecompilableAction)
			act = ((PrecompilableAction)act).precompile(value);
		else
			precompile = false;
		return new StoredAction(act, value, precompile);
	}

	@Override
	public String toString() {
		return action.getName().toUpperCase() + "=" + value;
	}
}
