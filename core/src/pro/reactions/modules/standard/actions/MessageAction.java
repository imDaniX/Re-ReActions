package pro.reactions.modules.standard.actions;

import pro.reactions.actions.Action;
import pro.reactions.activators.storages.Context;
import pro.reactions.util.Alias;

@Alias("msg")
public class MessageAction implements Action {
	@Override
	public boolean execute(Context context, String value) {
		return true;
	}

	@Override
	public String getName() {
		return "message";
	}
}
