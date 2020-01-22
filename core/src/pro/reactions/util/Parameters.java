package pro.reactions.util;

import pro.reactions.util.collections.InsensitiveStringMap;
import pro.reactions.util.math.NumbersUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Parameters implements Iterable<String> {
	private final String origin;
	private final InsensitiveStringMap<String> params;

	private Parameters(String origin, InsensitiveStringMap<String> params) {
		this.origin = origin;
		this.params = params;
	}

	public String getOrigin() {
		return origin;
	}

	public String getString(String key) {
		return getString(key, "");
	}

	public String getString(String key, String def) {
		return params.getOrDefault(key, def);
	}

	public double getDouble(String key) {
		return getDouble(key, 0);
	}

	public double getDouble(String key, double def) {
		return NumbersUtil.getDouble(params.get(key), def);
	}

	public int getInteger(String key) {
		return getInteger(key, 0);
	}

	public int getInteger(String key, int def) {
		return NumbersUtil.getInteger(params.get(key), def);
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean def) {
		String value = params.get(key);
		if(Util.isStringEmpty(value)) return def;
		if(key.equalsIgnoreCase("true"))
			return true;
		if(key.equalsIgnoreCase("false"))
			return false;
		return def;
	}

	public <R> R getParam(String key, Function<String, R> function) {
		return function.apply(params.get(key));
	}

	public <R> R getParam(String key, R def, Function<String, R> function) {
		R value = getParam(key, function);
		return value != null ? value : def;
	}

	public <R> R getParam(String key, R def, BiFunction<String, R, R> function) {
		return function.apply(params.get(key), def);
	}

	public boolean contains(String key) {
		return params.containsKey(key);
	}

	public boolean containsAny(String... keys) {
		return params.containsAnyKey(keys);
	}

	public boolean containsEvery(String... keys) {
		return params.containsEveryKey(keys);
	}

	public static Parameters fromMap(Map<String, String> map) {
		StringBuilder bld = new StringBuilder();
		InsensitiveStringMap<String> params = new InsensitiveStringMap<>(false, map);
		params.forEach((k, v) -> bld.append(k).append(":{").append(v).append("} "));
		String str = bld.toString();
		return new Parameters(str.isEmpty() ? str : str.substring(0, str.length() - 1), params);
	}

	public static Parameters fromString(String str) {
		InsensitiveStringMap<String> params = new InsensitiveStringMap<>(false);
		IterationState state = IterationState.SPACE;
		String param = "";
		StringBuilder text = new StringBuilder();
		StringBuilder bld = null;
		int brCount = 0;
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (state) {
				case SPACE:
					if(c == ' ') {
						text.append(c);
						continue;
					}
					bld = new StringBuilder().append(c);
					state = IterationState.TEXT;
					break;
				case TEXT:
					if(c == ' ') {
						text.append(bld).append(c);
						state = IterationState.SPACE;
						continue;
					}
					if(c == ':') {
						state = IterationState.DOTS;
						param = bld.toString();
						bld = new StringBuilder();
						continue;
					}
					bld.append(c);
					break;
				case DOTS:
					if(c == ' ') {
						text.append(bld).append(c);
						state = IterationState.SPACE;
						continue;
					}
					if(c == '{') {
						state = IterationState.BR_PARAM;
						continue;
					}
					state = IterationState.PARAM;
					bld.append(c);
					break;
				case PARAM:
					if(c == ' ') {
						state = IterationState.SPACE;
						String value = bld.toString();
						params.put(param, value);
						text.append('\u00A7').append('!').append(value.hashCode());
						continue;
					}
					bld.append(c);
					break;
				case BR_PARAM:
					if(c == '}') {
						if(brCount == 0) {
							state = IterationState.SPACE;
							String value = bld.toString();
							params.put(param, value);
							text.append('\u00A7').append('?').append(value.hashCode());
							continue;
						} else brCount--;
					} else if(c == '{')
						brCount++;
					bld.append(c);
					break;
			}
		}
		if(state == IterationState.PARAM) {
			params.put(param, bld.toString());
			text.append('\u00A7').append('!').append(param.hashCode());
		}
		params.put("param-line", str);
		return new Parameters(text.toString(), params);
	}

	@Override
	public String toString() {
		return origin;
	}

	@Override
	public Iterator<String> iterator() {
		return params.map().keySet().iterator();
	}

	public void forEach(BiConsumer<String, String> consumer) {
		params.map().forEach(consumer);
	}

	private enum IterationState {
		SPACE, TEXT, DOTS, PARAM, BR_PARAM
	}
}
