package pro.reactions.util.data;

import pro.reactions.util.Util;
import pro.reactions.util.math.NumbersUtil;

import java.util.Collections;
import java.util.List;

public final class DoubleValue implements DataValue {
	private double value;

	public DoubleValue(double value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		return Double.toString(value);
	}

	@Override
	public boolean asBoolean() {
		return value > 0;
	}

	@Override
	public int asInteger() {
		return (int) value;
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public List<String> asStringList() {
		return Collections.singletonList(asString());
	}

	@Override
	public boolean set(String value) {
		if(Util.isStringEmpty(value)) return false;
		if(NumbersUtil.FLOAT.matcher(value).matches()) {
			this.value = Double.parseDouble(value);
			return true;
		}
		return false;
	}

	@Override
	public boolean set(boolean value) {
		return false;
	}

	@Override
	public boolean set(int value) {
		this.value = value;
		return true;
	}

	@Override
	public boolean set(double value) {
		this.value = value;
		return true;
	}

	@Override
	public boolean set(List<String> value) {
		return false;
	}

	@Override
	public boolean set(Object value) {
		if(value instanceof Double) {
			this.value = (Double) value;
			return true;
		}
		return false;
	}
}
