package pro.reactions.placeholders;

import pro.reactions.ReActionsCore;
import pro.reactions.activators.storages.Context;
import pro.reactions.configuration.ConfigurationManager;
import pro.reactions.configuration.reload.ConfigurableObject;
import pro.reactions.configuration.reload.ReloadType;
import pro.reactions.configuration.reload.Reloadable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Reloadable(ReloadType.CONFIG)
public class PlaceholdersManager extends ConfigurableObject {
	private static final Pattern PLACEHOLDER_GREEDY = Pattern.compile("%\\S+%");
	private static final Pattern PLACEHOLDER_NONGREEDY = Pattern.compile("%\\S+?%");
	private final ConfigurationManager cfgManager;
	private int countLimit;

	public PlaceholdersManager() {
		cfgManager = ReActionsCore.INSTANCE.getConfiguration();
		countLimit = 127;
	}

	public boolean register(Placeholder ph) {
		if(ph == null) return false;
		return DefaultParsers.EQUALITY.add(ph) || DefaultParsers.PREFIXED.add(ph) ||
				DefaultParsers.CUSTOM.add(ph) || DefaultParsers.SIMPLE.add(ph);
	}

	public String parsePlaceholders(String text, Context context) {
		if(DefaultParsers.areEmpty() || text == null || text.length() < 3) return text;
		String oldText;
		int limit = countLimit;
		do {
			oldText = text;
			text = parseRecursive(text, PLACEHOLDER_GREEDY, context);
			text = parseRecursive(text, PLACEHOLDER_NONGREEDY, context);
		} while(--limit > 0 && !text.equals(oldText));
		return text;
	}

	private static String parseRecursive(String text, final Pattern phPattern, final Context context) {
		Matcher phMatcher = phPattern.matcher(text);
		// If found at least one
		if(phMatcher.find()) {
			StringBuffer buf = new StringBuffer();
			processIteration(buf, phMatcher, phPattern, context);
			while(phMatcher.find()) {
				processIteration(buf, phMatcher, phPattern, context);
			}
			return phMatcher.appendTail(buf).toString();
		}
		return text;
	}

	// Just some sh!tty stuff
	private static void processIteration(StringBuffer buffer, Matcher matcher, Pattern pattern, Context context) {
		matcher.appendReplacement(
			buffer,
			Matcher.quoteReplacement(
				DefaultParsers.process(
					parseRecursive(
						crop(matcher.group()),
						pattern,
						context
					),
					context
				)
			)
		);
	}

	private static String crop(String text) {
		return text.substring(1, text.length() - 1);
	}

	@Override
	public void reload(ReloadType cause, boolean firstTime) {
		countLimit = cfgManager.getData("ph-limit").asInteger();
	}
}