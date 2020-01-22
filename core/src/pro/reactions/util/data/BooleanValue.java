package pro.reactions.util.data;

import pro.reactions.util.Util;

import java.util.Collections;
import java.util.List;

public final class BooleanValue implements DataValue {
	private boolean value;

	public BooleanValue(boolean value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		return Boolean.toString(value);
	}

	@Override
	public boolean asBoolean() {
		return value;
	}

	@Override
	public int asInteger() {
		return value ? 1 : 0;
	}

	@Override
	public double asDouble() {
		return asInteger();
	}

	@Override
	public List<String> asStringList() {
		return Collections.singletonList(asString());
	}

	@Override
	public boolean set(String value) {
		if(Util.isStringEmpty(value)) return false;
		if(value.equalsIgnoreCase("true"))
			this.value = true;
		else if(value.equalsIgnoreCase("false"))
			this.value = false;
		else
			return false;
		return true;
	}

	@Override
	public boolean set(boolean value) {
		this.value = value;
		return true;
	}

	@Override
	public boolean set(Object value) {
		if(value instanceof Boolean) {
			this.value = (Boolean) value;
			return true;
		}
		return false;
	}
}
