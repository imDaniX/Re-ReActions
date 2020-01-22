package pro.reactions.activators.storages;

import pro.reactions.util.collections.InsensitiveStringMap;
import pro.reactions.util.data.DataValue;
import pro.reactions.util.data.special.VoidValue;

import java.util.Map;

public class Changeables {
	public final static Changeables EMPTY = new Changeables();

	private final InsensitiveStringMap<DataValue> changeables;

	private Changeables() {
		this.changeables = InsensitiveStringMap.emptyMap();
	}

	private Changeables(Map<String, DataValue> changeables) {
		if(changeables == null || changeables.isEmpty()) {
			this.changeables = InsensitiveStringMap.emptyMap();
		} else {
			this.changeables = new InsensitiveStringMap<>(false);
			for(String key : changeables.keySet())
				this.changeables.put(key, changeables.get(key));
		}
	}

	private Changeables(InsensitiveStringMap<DataValue> changeables) {
		if(changeables == null || changeables.isEmpty()) {
			this.changeables = InsensitiveStringMap.emptyMap();
		} else {
			this.changeables = new InsensitiveStringMap<>(false, changeables);
		}
	}

	public boolean setValue(String key, String value) {
		DataValue dataValue = changeables.get(key);
		if(dataValue == null) return false;
		return dataValue.set(value);
	}

	public boolean setValue(String key, int value) {
		DataValue dataValue = changeables.get(key);
		if(dataValue == null) return false;
		return dataValue.set(value);
	}

	public boolean setValue(String key, double value) {
		DataValue dataValue = changeables.get(key);
		if(dataValue == null) return false;
		return dataValue.set(value);
	}

	public boolean setValue(String key, boolean value) {
		DataValue dataValue = changeables.get(key);
		if(dataValue == null) return false;
		return dataValue.set(value);
	}

	public boolean setValue(String key, Object value) {
		DataValue dataValue = changeables.get(key);
		if(dataValue == null) return false;
		return dataValue.set(value);
	}

	public DataValue getValue(String key) {
		return changeables.getOrDefault(key, VoidValue.INSTANCE);
	}

	public void fillStringMap(InsensitiveStringMap<String> map) {
		for(String key : changeables.keySet())
			map.put(key, changeables.get(key).asString());
	}

	public boolean isEmpty() {
		return changeables.isEmpty();
	}

	public static Changeables fromMap(Map<String, DataValue> changeables) {
		if(changeables == null || changeables.isEmpty())
			return EMPTY;
		return new Changeables(changeables);
	}

	public static Changeables fromMap(InsensitiveStringMap<DataValue> changeables) {
		if(changeables == null || changeables.isEmpty())
			return EMPTY;
		return new Changeables(changeables);
	}
}
