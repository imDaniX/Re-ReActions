package pro.reactions.util.collections;

import java.util.AbstractList;

// Pretty useless due to Arrays.asList
public class WrappedArrayList<E> extends AbstractList<E> {
	private final E[] origin;

	public WrappedArrayList(E[] origin) {
		this.origin = origin;
	}

	@Override
	public E get(int i) {
		return origin[i];
	}

	@Override
	public E set(int i, E value) {
		E old = origin[i];
		origin[i] = value;
		return old;
	}

	@Override
	public int size() {
		return origin.length;
	}
}
