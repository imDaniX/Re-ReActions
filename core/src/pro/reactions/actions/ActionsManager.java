package pro.reactions.actions;

import pro.reactions.ReActionsCore;
import pro.reactions.activators.storages.Context;
import pro.reactions.platform.ThreadHelper;
import pro.reactions.util.Util;
import pro.reactions.util.collections.InsensitiveStringMap;

import java.util.Collections;
import java.util.List;

public class ActionsManager {
	private final InsensitiveStringMap<Action> actions;
	private final ThreadHelper threader;

	public ActionsManager() {
		actions = new InsensitiveStringMap<>(true);
		threader = ReActionsCore.INSTANCE.getThreader();
	}

	public boolean register(Action action) {
		if(action == null) return false;
		if(actions.containsKey(action.getName())) return false;
		actions.put(action.getName(), action);
		for(String alias : Util.getAliases(action))
			actions.putIfAbsent(alias, action);
		return true;
	}

	public Action getAction(String name) {
		return actions.get(name);
	}

	public void executeActions(List<StoredAction> actions, Context context) {
		for(int i = 0; i < actions.size(); i++) {
			StoredAction action = actions.get(i);
			if(context.isAsync() && !action.getAction().isAsync()) {
				threader.runSync(() -> action.execute(context));
				continue;
			}
			if(action.execute(context) && action.getAction() instanceof Stopper) {
				((Stopper)action.getAction()).stop(Collections.unmodifiableList(actions.subList(i, actions.size())));
				break;
			}
		}
	}
}
