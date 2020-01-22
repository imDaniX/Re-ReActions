package pro.reactions.time;

import pro.reactions.ReActionsCore;
import pro.reactions.platform.RaPlatform;
import pro.reactions.util.Util;
import pro.reactions.util.math.NumbersUtil;

import java.util.regex.Pattern;

public class TimeUtil {
	private final static RaPlatform platform = ReActionsCore.INSTANCE.getPlatform();
	private final static Pattern TIME_MM_SS = Pattern.compile("\\d+:[0-5][0-9]");
	private final static Pattern TIME_HH_MM_SS = Pattern.compile("\\d+:[0-5][0-9]:[0-5][0-9]");

	public static long parseTime(String str, boolean units) {
		if(Util.isStringEmpty(str)) return 0;
		long time = 0;
		if(str.contains(":")) {
			// TODO: Unify
			if(TIME_MM_SS.matcher(str).matches()) {
				String minutes = str.substring(0, str.indexOf(':'));
				String seconds = str.substring(minutes.length() + 1);
				time = NumbersUtil.getInteger(minutes, 0) * 60000 +
						NumbersUtil.getInteger(seconds, 0) * 1000;
			} else if(TIME_HH_MM_SS.matcher(str).matches()) {
				String minutes = str.substring(0, str.indexOf(':'));
				String seconds = str.substring(minutes.length() + 1);
				String hours = str.substring(str.lastIndexOf(':') + 1);
				time = NumbersUtil.getInteger(hours, 0) * 3600000 +
						NumbersUtil.getInteger(minutes, 0) * 60000 +
						NumbersUtil.getInteger(seconds, 0) * 1000;
			}
		} else {
			String timeStr = splitTime(str);
			time = NumbersUtil.getInteger(timeStr, 0);
			String type = str.substring(timeStr.length());
			switch(type.toLowerCase()) {
				// milliseconds
				case "ms": break;
				// units or ticks
				case "u": case "t":
					if(units) return time;
					time *= platform.timeUnit();
					break;
				// minutes
				case "m": time *= 60000; break;
				// hours
				case "h": time *= 3600000; break;
				// days
				case "d": time *= 86400000; break;
				// seconds or invalid
				default: time *= 1000;
			}
		}
		return units ? Math.round(time/platform.timeUnit()) : time;
	}

	private static String splitTime(String str) {
		StringBuilder bld = new StringBuilder();
		for(char c : str.toCharArray()) {
			if(c >= '0' && c <= '9') {
				bld.append(c);
				continue;
			}
			break;
		}
		return bld.toString();
	}
}
