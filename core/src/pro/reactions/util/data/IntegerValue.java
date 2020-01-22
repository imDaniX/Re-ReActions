package pro.reactions.util.data;

import pro.reactions.util.Util;
import pro.reactions.util.math.NumbersUtil;

import java.util.Collections;
import java.util.List;

public final class IntegerValue implements DataValue {
	private int value;

	public IntegerValue(int value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		return Integer.toString(value);
	}

	@Override
	public boolean asBoolean() {
		return value > 0;
	}

	@Override
	public int asInteger() {
		return value;
	}

	@Override
	public double asDouble() {
		return asInteger();
	}

	@Override
	public List<String> asStringList() {
		return Collections.singletonList(toString());
	}

	@Override
	public boolean set(String value){
		if(Util.isStringEmpty(value)) return false;
		if(NumbersUtil.INT.matcher(value).matches()) {
			this.value = Integer.parseInt(value);
			return true;
		}
		return false;
	}

	@Override
	public boolean set(int value) {
		this.value = value;
		return true;
	}

	@Override
	public boolean set(double value) {
		this.value = NumbersUtil.safeLongToInt(Math.round(value));
		return false;
	}

	@Override
	public boolean set(List<String> value) {
		return false;
	}

	@Override
	public boolean set(Object value) {
		if(value instanceof Integer) {
			this.value = (Integer) value;
			return true;
		}
		return false;
	}
}
