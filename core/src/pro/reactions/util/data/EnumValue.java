package pro.reactions.util.data;

import pro.reactions.util.Util;

import java.util.Collections;
import java.util.List;

public final class EnumValue implements DataValue {
	private Enum value;

	public EnumValue(Enum value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		return value.name();
	}

	@Override
	public boolean asBoolean() {
		return true;
	}

	@Override
	public int asInteger() {
		return value.ordinal();
	}

	@Override
	public double asDouble() {
		return asInteger();
	}

	@Override
	public List<String> asStringList() {
		return Collections.singletonList(asString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean set(String value) {
		if(Util.isStringEmpty(value)) return false;
		try {
			this.value = Enum.valueOf(this.value.getClass(), value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public boolean set(Object value) {
		if(this.value.getClass().isInstance(value)) {
			this.value = (Enum) value;
			return true;
		}
		return false;
	}
}
