package pro.reactions.flags;

import pro.reactions.activators.storages.Context;
import pro.reactions.util.Util;
import pro.reactions.util.collections.InsensitiveStringMap;

import java.util.List;

public class FlagsManager {
	private final InsensitiveStringMap<Flag> flags;

	public FlagsManager() {
		flags = new InsensitiveStringMap<>(true);
	}

	public boolean register(Flag flag) {
		if(flag == null) return false;
		if(flags.containsKey(flag.getName())) return false;
		for(String alias : Util.getAliases(flag))
			flags.putIfAbsent(alias, flag);
		flags.put(flag.getName(), flag);
		return true;
	}

	public Flag getFlag(String name) {
		return flags.get(name);
	}

	public boolean checkFlags(List<StoredFlag> flags, Context context) {
		return flags.stream().allMatch(f -> f.check(context));
	}
}
