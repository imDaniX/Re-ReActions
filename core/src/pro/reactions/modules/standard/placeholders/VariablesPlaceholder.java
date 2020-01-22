package pro.reactions.modules.standard.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.Placeholder;
import pro.reactions.util.Alias;

@Alias("varp")
public class VariablesPlaceholder implements Placeholder.Prefixed {
	@Override
	public String processPlaceholder(String ph, String text, Context context) {
		// TODO
		return null;
	}

	@Override
	public String getPrefix() {
		return "var";
	}
}
