package pro.reactions.util.data.special;

import pro.reactions.util.data.BooleanValue;
import pro.reactions.util.data.DataValue;
import pro.reactions.util.data.DoubleValue;
import pro.reactions.util.data.IntegerValue;
import pro.reactions.util.data.StringListValue;
import pro.reactions.util.data.StringValue;

import java.util.List;

public final class DynamicValue implements DataValue {
	private DataValue origin;

	public DynamicValue() {
		this.origin = VoidValue.INSTANCE;
	}

	public DynamicValue(DataValue origin) {
		this.origin = origin;
	}

	@Override
	public Object value() {
		return origin.value();
	}

	@Override
	public String asString() {
		return origin.asString();
	}

	@Override
	public boolean asBoolean() {
		return origin.asBoolean();
	}

	@Override
	public int asInteger() {
		return origin.asInteger();
	}

	@Override
	public double asDouble() {
		return origin.asDouble();
	}

	@Override
	public List<String> asStringList() {
		return origin.asStringList();
	}

	@Override
	public boolean set(String value) {
		if(!origin.set(value))
			origin = new StringValue(value);
		return true;
	}

	@Override
	public boolean set(boolean value) {
		if(!origin.set(value))
			origin = new BooleanValue(value);
		return true;
	}

	@Override
	public boolean set(int value) {
		if(!origin.set(value))
			origin = new IntegerValue(value);
		return true;
	}

	@Override
	public boolean set(double value) {
		if(!origin.set(value))
			origin = new DoubleValue(value);
		return true;
	}

	@Override
	public boolean set(List<String> value) {
		if(!origin.set(value))
			origin = new StringListValue(value);
		return true;
	}

	@Override
	public boolean set(Object value) {
		return origin.set(value);
	}
}
