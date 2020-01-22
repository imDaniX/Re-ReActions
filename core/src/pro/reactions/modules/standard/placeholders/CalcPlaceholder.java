package pro.reactions.modules.standard.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.Placeholder;
import pro.reactions.util.Alias;
import pro.reactions.util.math.MathEvaluator;

@Alias("calculate")
public class CalcPlaceholder implements Placeholder.Prefixed {
	@Override
	public String processPlaceholder(String ph, String text, Context context) {
		return Double.toString(MathEvaluator.eval(text));
	}

	@Override
	public String getPrefix() {
		return "calc";
	}
}
