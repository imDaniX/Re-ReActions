package pro.reactions.configuration;

import pro.reactions.util.data.DataValue;

import java.util.List;

public interface RaConfigSection {
	DataValue get(String s);
	DataValue get(String s, DataValue def);
	boolean getBoolean(String s);
	boolean getBoolean(String s, boolean def);
	int getInteger(String s);
	int getInteger(String s, int def);
	double getDouble(String s);
	double getDouble(String s, double def);
	String getString(String s);
	String getString(String s, String def);
	List<String> getStringList(String s);
	List<String> getStringList(String s, List<String> def);

	RaConfigSection getSection(String s);

	void set(String s, Object value);
	void set(String s, boolean value);
	void set(String s, int value);
	void set(String s, double value);
	void set(String s, String value);
	void set(String s, List<String> value);
	default void set(String s, DataValue value) {
		set(s, value.value());
	}

	boolean containsKey(String s);
	String[] getKeys();
}
