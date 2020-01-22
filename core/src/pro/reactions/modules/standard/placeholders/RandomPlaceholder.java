package pro.reactions.modules.standard.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.Placeholder;
import pro.reactions.util.Alias;
import pro.reactions.util.Util;
import pro.reactions.util.math.NumbersUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Alias("rnd")
public class RandomPlaceholder implements Placeholder.Prefixed {
	private final static Pattern FLOAT_MIN_MAX = Pattern.compile("(-?\\d+(\\.\\d+)?)-(-?\\d+(\\.\\d+)?)");

	@Override
	public String processPlaceholder(String ph, String text, Context context) {
		double r0 = NumbersUtil.getDouble(text, 0);
		if(r0 != 0) return Double.toString(NumbersUtil.randomDouble(r0));
		Matcher matcher = FLOAT_MIN_MAX.matcher(text);
		if(matcher.matches()) {
			double r1 = NumbersUtil.getDouble(matcher.group(1), 0);
			double r2 = NumbersUtil.getDouble(matcher.group(2), r1);
			return Double.toString(NumbersUtil.randomDouble(r1, r2));
		} else {
			List<String> split = Util.splitByChar(text, ',');
			if(split.size() < 1) return text;
			return split.get(NumbersUtil.randomInteger(split.size()) - 1);
		}
	}

	@Override
	public String getPrefix() {
		return "random";
	}
}
