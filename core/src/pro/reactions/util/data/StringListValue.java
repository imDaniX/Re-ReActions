package pro.reactions.util.data;

import java.util.Arrays;
import java.util.List;

public final class StringListValue implements DataValue {
	private List<String> value;

	public StringListValue(List<String> value) {
		this.value = value;
	}

	@Override
	public Object value() {
		return value;
	}

	@Override
	public String asString() {
		StringBuilder bld = new StringBuilder();
		value.forEach(s -> bld.append(s).append("\n"));
		return bld.toString();
	}

	@Override
	public boolean asBoolean() {
		return !value.isEmpty();
	}

	@Override
	public int asInteger() {
		return value.size();
	}

	@Override
	public double asDouble() {
		return asInteger();
	}

	@Override
	public List<String> asStringList() {
		return value;
	}

	@Override
	public boolean set(String value) {
		this.value = Arrays.asList(value.split("\n"));
		return true;
	}

	@Override
	public boolean set(List<String> value) {
		this.value = value;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean set(Object value) {
		if(value instanceof List) {
			if(((List) value).stream().allMatch(t -> t instanceof String)) {
				this.value = (List<String>) value;
				return true;
			}
		}
		return false;
	}
}
