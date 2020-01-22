package pro.reactions.util.data;

import java.util.Collections;
import java.util.List;

public final class StringValue implements DataValue {
	private String value;

	public StringValue(String value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		return value;
	}

	@Override
	public boolean asBoolean() {
		return Boolean.valueOf(value);
	}

	@Override
	public int asInteger() {
		return value.length();
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
		this.value = value;
		return true;
	}

	@Override
	public boolean set(boolean value) {
		this.value = Boolean.toString(value);
		return true;
	}

	@Override
	public boolean set(int value) {
		this.value = Integer.toString(value);
		return true;
	}

	@Override
	public boolean set(double value) {
		this.value = Double.toString(value);
		return true;
	}

	@Override
	public boolean set(List<String> value) {
		StringBuilder bld = new StringBuilder();
		value.forEach(s -> bld.append(s).append("\n"));
		this.value = bld.toString();
		this.value = this.value.substring(0, this.value.length() - 1);
		return true;
	}

	@Override
	public boolean set(Object value) {
		if(value instanceof String) {
			this.value = (String) value;
			return true;
		}
		return false;
	}
}
