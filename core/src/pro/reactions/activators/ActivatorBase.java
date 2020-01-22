package pro.reactions.activators;

import pro.reactions.actions.StoredAction;
import pro.reactions.configuration.RaConfigSection;
import pro.reactions.flags.StoredFlag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ActivatorBase {
	private final String name;
	private final String group;
	private List<StoredAction> actions;
	private List<StoredAction> reactions;
	private List<StoredFlag> flags;

	public ActivatorBase(String name, String group) {
		this.name = name;
		this.group = group;
		actions = new ArrayList<>();
		reactions = new ArrayList<>();
		flags = new ArrayList<>();
	}

	public ActivatorBase(String name, String group, RaConfigSection cfg) {
		this.name = name;
		this.group = group;
		loadData(cfg);
	}

	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}

	public List<StoredAction> getActions() {
		return actions;
	}

	public List<StoredAction> getReactions() {
		return reactions;
	}

	public List<StoredFlag> getFlags() {
		return flags;
	}

	public void addAction(StoredAction action, boolean act) {
		List<StoredAction> actions = act ? this.actions : this.reactions;
		actions.add(action);
	}

	public boolean setAction(StoredAction action, int i, boolean act) {
		List<StoredAction> actions = act ? this.actions : this.reactions;
		if(actions.size() <= i) return false;
		actions.set(i, action);
		return true;
	}

	public void addFlag(StoredFlag flag) {
		flags.add(flag);
	}

	public boolean setFlag(StoredFlag flag, int i) {
		if(flags.size() <= i) return false;
		flags.set(i, flag);
		return true;
	}

	private void loadData(RaConfigSection cfg) {
		actions = new ArrayList<>();
		for(String line : cfg.getStringList("actions", Collections.emptyList())) {
			StoredAction action = StoredAction.fromString(line);
			if(action != null) actions.add(action);
		}
		reactions = new ArrayList<>();
		for(String line : cfg.getStringList("reactions", Collections.emptyList())) {
			StoredAction action = StoredAction.fromString(line);
			if(action != null) reactions.add(action);
		}
		flags = new ArrayList<>();
		for(String line : cfg.getStringList("flags", Collections.emptyList())) {
			StoredFlag flag = StoredFlag.valueOf(line);
			if(flag != null) flags.add(flag);
		}
	}
}
