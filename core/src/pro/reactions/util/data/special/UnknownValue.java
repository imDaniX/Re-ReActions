package pro.reactions.util.data.special;

import pro.reactions.util.data.DataValue;

import java.util.Collections;
import java.util.List;

public final class UnknownValue implements DataValue {
	private Object value;

	public UnknownValue(Object value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		return value.toString();
	}

	@Override
	public boolean asBoolean() {
		return asInteger() >= 0;
	}

	@Override
	public int asInteger() {
		return value.hashCode();
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
		return set((Object)value);
	}

	@Override
	public boolean set(boolean value) {
		return set((Object)value);
	}

	@Override
	public boolean set(int value) {
		return set((Object)value);
	}

	@Override
	public boolean set(double value) {
		return set((Object)value);
	}

	@Override
	public boolean set(List<String> value) {
		return set((Object)value);
	}

	@Override
	public boolean set(Object value) {
		if(this.value.getClass().isInstance(value)) {
			this.value = value;
			return true;
		}
		return false;
	}
}
