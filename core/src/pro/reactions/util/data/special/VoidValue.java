package pro.reactions.util.data.special;

import pro.reactions.util.data.DataValue;

import java.util.Collections;
import java.util.List;

public final class VoidValue implements DataValue {
	public static final VoidValue INSTANCE = new VoidValue();

	private VoidValue() {
	}

	@Override
	public Object value() {
		return new Object();
	}

	@Override
	public String asString() {
		return "";
	}

	@Override
	public boolean asBoolean() {
		return false;
	}

	@Override
	public int asInteger() {
		return 0;
	}

	@Override
	public double asDouble() {
		return 0;
	}

	@Override
	public List<String> asStringList() {
		return Collections.emptyList();
	}

	@Override
	public boolean set(String value) {
		return false;
	}

	@Override
	public boolean set(boolean value) {
		return false;
	}

	@Override
	public boolean set(int value) {
		return false;
	}

	@Override
	public boolean set(double value) {
		return false;
	}

	@Override
	public boolean set(List<String> value) {
		return false;
	}

	@Override
	public boolean set(Object value) {
		return false;
	}
}
