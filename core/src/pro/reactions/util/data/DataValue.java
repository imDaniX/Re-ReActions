package pro.reactions.util.data;

import pro.reactions.util.data.special.DynamicValue;
import pro.reactions.util.data.special.UnknownValue;
import pro.reactions.util.data.special.VoidValue;

import java.util.List;

public interface DataValue {
	Object value();

	String asString();
	boolean asBoolean();
	int asInteger();
	double asDouble();
	List<String> asStringList();

	boolean set(String value);

	default boolean set(boolean value) {
		return false;
	}
	default boolean set(int value) {
		return false;
	}
	default boolean set(double value) {
		return false;
	}
	default boolean set(List<String> value) {
		return false;
	}

	boolean set(Object value);

	@SuppressWarnings("unchecked")
	static DataValue getDataValue(Object obj) {
		if(obj == null) {
			return VoidValue.INSTANCE;
		} else if(obj instanceof String) {
			return new StringValue((String) obj);
		} else if(obj instanceof Double) {
			return new DoubleValue((Double) obj);
		} else if(obj instanceof Integer) {
			return new IntegerValue((Integer) obj);
		} else if(obj instanceof Boolean) {
			return new BooleanValue((Boolean) obj);
		} else if(obj instanceof Enum) {
			return new EnumValue((Enum) obj);
		} else if(obj instanceof List) {
			// Still not 100%
			if(((List) obj).stream().allMatch(t -> t instanceof String))
				return new StringListValue((List<String>) obj);
		}
		return new UnknownValue(obj);
	}

	default DynamicValue toDynamic() {
		return this instanceof DynamicValue ? (DynamicValue) this : new DynamicValue(this);
	}
}
