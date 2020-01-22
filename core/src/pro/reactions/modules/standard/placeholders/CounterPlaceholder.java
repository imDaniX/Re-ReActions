package pro.reactions.modules.standard.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.Placeholder;
import pro.reactions.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CounterPlaceholder implements Placeholder.Prefixed {
	private final static Integer ZERO = 0;
	// TODO: To cache
	private final Map<String, Integer> counters;

	public CounterPlaceholder() {
		this.counters = new HashMap<>();
	}

	@Override
	public String processPlaceholder(String ph, String text, Context context) {
		List<String> split = Util.splitByChar(text, '.');
		if(split.size() < 1) {
			return "0";
		} else
		if(split.size() == 1) {
			Integer i = counters.get(text);
			if(i == null) {
				counters.put(text, ZERO);
				return "0";
			}
			return i.toString();
		} else {
			String id = split.get(0);
			Integer i = counters.getOrDefault(id, ZERO);
			switch(split.get(1)) {
				case "up": case "inc": case "+":
					i++; break;
				case "down": case "dec": case "-":
					i--; break;
				case "reset": case "re": case "0":
					i = ZERO; break;
				case "delete": case "del": case "/":
					counters.remove(id); return "0";
			}
			counters.put(id, i);
			return i.toString();
		}
	}

	@Override
	public String getPrefix() {
		return "counter";
	}
}
