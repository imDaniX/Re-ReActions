package pro.reactions.modules.standard.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.Placeholder;

public class TempvarsPlaceholder implements Placeholder {
	@Override
	public String processPlaceholder(String ph, String text, Context context) {
		return context.getTempVariables().getCased(ph);
	}
}
