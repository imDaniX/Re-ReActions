package pro.reactions.util.math;

import pro.reactions.util.Util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class NumbersUtil {
	public final static Pattern INT = Pattern.compile("-?\\d+");
	public final static Pattern FLOAT = Pattern.compile("-?\\d+(\\.\\d+)?");

	private NumbersUtil() {
	}

	public static double randomDouble(double max) {
		if(max == 0) return 0;
		return max > 0 ? ThreadLocalRandom.current().nextDouble(max) : -ThreadLocalRandom.current().nextDouble(-max);
	}

	public static double randomDouble(double min, double max) {
		return min > max ? randomDouble(max, min) : ThreadLocalRandom.current().nextDouble(min, max);
	}

	public static int randomInteger(int max) {
		if(max == 0) return 0;
		return max > 0 ? ThreadLocalRandom.current().nextInt(max) : -ThreadLocalRandom.current().nextInt(-max);
	}

	public static int randomInteger(int min, int max) {
		return min > max ? randomInteger(max, min) : ThreadLocalRandom.current().nextInt(min, max);
	}

	public static long randomLong(long max) {
		if(max == 0) return 0;
		return max > 0 ? ThreadLocalRandom.current().nextLong(max) : -ThreadLocalRandom.current().nextLong(-max);
	}

	public static long randomLong(long min, long max) {
		return min > max ? randomLong(max, min) : ThreadLocalRandom.current().nextLong(min, max);
	}

	public static int getInteger(String str, int def) {
		if(Util.isStringEmpty(str) || !INT.matcher(str).matches()) return def;
		return Integer.parseInt(str);
	}

	public static double getDouble(String str, double def) {
		if(Util.isStringEmpty(str) || !FLOAT.matcher(str).matches()) return def;
		return Double.parseDouble(str);
	}

	public static int safeLongToInt(long l) {
		if(l > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		if(l < Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		return (int)l;
	}

	public static int safeDoubleToInt(double d) {
		if(d > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		if(d < Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		return (int)d;
	}
}
