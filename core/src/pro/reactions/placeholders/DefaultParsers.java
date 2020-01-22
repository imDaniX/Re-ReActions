package pro.reactions.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Default parsers that will be used first
 */
enum DefaultParsers implements Parser {
	/**
	 * For {@link Placeholder.Equality} placeholders
	 * Checks whole placeholder
	 */
	EQUALITY {
		private final Map<String, Placeholder> placeholders = new HashMap<>();

		@Override
		public boolean add(Placeholder ph) {
			if(ph instanceof Placeholder.Equality) {
				String id = ((Placeholder.Equality)ph).getId();
				if(placeholders.containsKey(id)) return false;
				placeholders.put(id, ph);
				for(String alias : Util.getAliases(ph))
					placeholders.putIfAbsent(alias.toLowerCase(), ph);
				return true;
			}
			return false;
		}

		@Override
		public String parse(String text, Context context) {
			Placeholder ph = placeholders.get(text);
			if(ph == null) return null;
			return ph.processPlaceholder(text, text, context);
		}

		@Override
		public boolean isEmpty() {
			return placeholders.isEmpty();
		}
	},

	/**
	 * For {@link Placeholder.Prefixed} placeholders
	 * Checks placeholder if it has "%prefix:text%" format
	 */
	PREFIXED {
		private final Map<String, Placeholder> placeholders = new HashMap<>();

		@Override
		public boolean add(Placeholder ph) {
			if(ph instanceof Placeholder.Prefixed) {
				String prefix = ((Placeholder.Prefixed)ph).getPrefix();
				if(placeholders.containsKey(prefix)) return false;
				placeholders.put(prefix, ph);
				for(String alias : Util.getAliases(ph))
					placeholders.putIfAbsent(alias.toLowerCase(), ph);
				return true;
			}
			return false;
		}

		@Override
		public String parse(String text, Context context) {
			int index = text.indexOf(':');
			if(index < 1) return null;
			String prefix = text.substring(0, index);
			Placeholder ph = placeholders.get(prefix);
			if(ph == null) return null;
			return ph.processPlaceholder(text.substring(index + 1), text, context);
		}

		@Override
		public boolean isEmpty() {
			return placeholders.isEmpty();
		}
	},

	/**
	 * For {@link Placeholder.Custom} placeholders
	 * Stores custom {@link Parser}s
	 */
	CUSTOM {
		private final Map<Class<? extends Parser>, Parser> parsers = new HashMap<>();

		@Override
		public boolean add(Placeholder ph) {
			if(ph instanceof Placeholder.Custom) {
				Parser parser = ((Placeholder.Custom)ph).getParser();
				if(parser == null) return false;
				Parser mappedParser = parsers.get(parser.getClass());
				if(mappedParser == null) {
					parsers.put(parser.getClass(), parser);
					return parser.add(ph);
				} else return mappedParser.add(ph);
			}
			return false;
		}

		@Override
		public String parse(String text, Context context) {
			for(Parser handler : parsers.values()) {
				String result = handler.parse(text, context);
				if(result != null) return result;
			}
			return null;
		}

		@Override
		public boolean isEmpty() {
			return parsers.isEmpty() || parsers.values().stream().allMatch(Parser::isEmpty);
		}
	},

	/**
	 * For {@link Placeholder} placeholders without any other default interfaces
	 * Just tries to check all stored placeholders - all the logic inside placeholders itself
	 */
	SIMPLE {
		private final Set<Placeholder> placeholders = new HashSet<>();

		@Override
		public boolean add(Placeholder ph) {
			if(ph == null) return false;
			placeholders.add(ph);
			return true;
		}

		@Override
		public String parse(String text, Context context) {
			for(Placeholder ph : placeholders) {
				String result = ph.processPlaceholder(text, text, context);
				if(result != null) return result;
			}
			return null;
		}

		@Override
		public boolean isEmpty() {
			return placeholders.isEmpty();
		}
	};

	/**
	 * Check if all the default parsers are empty
	 * @return Are parsers empty
	 */
	public static boolean areEmpty() {
		for(DefaultParsers parser : values())
			if(!parser.isEmpty()) return false;
		return true;
	}

	/**
	 * Parse and process placeholder
	 * @param text Text to parse
	 * @param context Context of activation
	 * @return Placeholder or %{@param text}% if not found
	 */
	public static String process(String text, final Context context) {
		String cased = text.toLowerCase();
		for(Parser ph : values()) {
			String replacement = ph.parse(cased, context);
			if(replacement == null) continue;
			return replacement;
		}
		return "%" + text + "%";
	}
}
