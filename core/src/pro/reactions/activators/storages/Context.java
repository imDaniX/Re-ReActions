package pro.reactions.activators.storages;

import pro.reactions.platform.RaPlayer;
import pro.reactions.util.collections.InsensitiveStringMap;

public class Context {
	private final Changeables changeables;
	private final InsensitiveStringMap<String> tempVariables;
	private final RaPlayer player;
	private final boolean async;

	public Context(RaPlayer player, boolean async) {
		this.changeables = Changeables.EMPTY;
		this.tempVariables = new InsensitiveStringMap<>(false);
		this.player = player;
		this.async = async;
	}

	public Context(Changeables changeables, InsensitiveStringMap<String> tempVariables, RaPlayer player, boolean async) {
		if(changeables == null || changeables.isEmpty())
			this.changeables = Changeables.EMPTY;
		else
			this.changeables = changeables;
		this.tempVariables = new InsensitiveStringMap<>(false);
		if(tempVariables != null && !tempVariables.isEmpty())
			this.tempVariables.putAll(tempVariables);
		this.changeables.fillStringMap(this.tempVariables);
		this.player = player;
		this.async = async;
	}

	public boolean setChangeable(String key, String value) {
		key = key.toLowerCase();
		if(changeables.setValue(key, value)) {
			tempVariables.put(key, value);
			return true;
		}
		return false;
	}

	public boolean setChangeable(String key, int value) {
		key = key.toLowerCase();
		if(changeables.setValue(key, value)) {
			tempVariables.put(key, Integer.toString(value));
			return true;
		}
		return false;
	}

	public boolean setChangeable(String key, double value) {
		key = key.toLowerCase();
		if(changeables.setValue(key, value)) {
			tempVariables.put(key, Double.toString(value));
			return true;
		}
		return false;
	}

	public boolean setChangeable(String key, boolean value) {
		key = key.toLowerCase();
		if(changeables.setValue(key, value)) {
			tempVariables.put(key, Boolean.toString(value));
			return true;
		}
		return false;
	}

	public boolean setChangeable(String key, Object value) {
		key = key.toLowerCase();
		if(changeables.setValue(key, value)) {
			tempVariables.put(key, value.toString());
			return true;
		}
		return false;
	}

	public RaPlayer getPlayer() {
		return player;
	}

	public InsensitiveStringMap<String> getTempVariables() {
		return tempVariables;
	}

	public boolean isAsync() {
		return async;
	}
}
