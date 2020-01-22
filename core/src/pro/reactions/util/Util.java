package pro.reactions.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Util {
	private static final String[] EMPTY_ARRAY = new String[0];

	private Util() {
	}

	public static boolean isStringEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static String clr(String s) {
		return clr('&', s);
	}

	public static String clr(char clr, String s) {
		char[] arr = s.toCharArray();
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] == clr && isClrChar(arr[i+1]))
				arr[i] = '\u00A7';
		}
		return new String(arr);
	}

	private static boolean isClrChar(char c) {
		return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'r') || (c >= 'A' && c <= 'R');
	}

	public static List<String> splitByChar(String str, char c) {
		if(str.length() < 1) return Collections.emptyList();
		List<String> list = new ArrayList<>();
		splitByChar(list, str, c);
		return list;
	}

	private static void splitByChar(List<String> list, String str, char c) {
		int index = str.indexOf(c);
		if(index < 1) {
			list.add(str);
		} else {
			list.add(str.substring(0, index));
			splitByChar(list, str.substring(index+1), c);
		}
	}

	public static <T> T safeCast(Class<T> clazz, Object obj) {
		return clazz.isInstance(obj) ? clazz.cast(obj) : null;
	}

	public static <T extends Enum<T>> T getEnum(Class<T> clazz, String name) {
		return getEnum(clazz, name, null);
	}

	public static <T extends Enum<T>> T getEnum(Class<T> clazz, String name, T def) {
		try {
			return Enum.valueOf(clazz, name);
		} catch (IllegalArgumentException e) {
			return def;
		}
	}

	public static String removeSpaces(String str) {
		StringBuilder bld = new StringBuilder();
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(c != ' ') bld.append(c);
		}
		return bld.toString();
	}

	public static int hashes(Object... objs) {
		int i = 0;
		for(Object obj : objs)
			i += obj.hashCode();
		return i;
	}

	public static <T extends Collection<String>> T copyPartialMatches(String token, Iterable<String> originals, T collection) {
		for (String string : originals) {
			if (startsWithIgnoreCase(string, token)) {
				collection.add(string);
			}
		}

		return collection;
	}

	public static boolean startsWithIgnoreCase(String string, String prefix) {
		return string.length() >= prefix.length() && string.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	public static <T> boolean arrayContains(T[] arr, T search) {
		for(T t : arr)
			if(t == search) return true;
		return false;
	}

	public static String[] getAliases(Object obj) {
		Class<?> clazz = obj.getClass();
		if(clazz.isAnnotationPresent(Alias.class)) {
			return clazz.getAnnotation(Alias.class).value();
		}
		return EMPTY_ARRAY;
	}
}
