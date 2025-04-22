package net.spy.memcached.internal;

import java.lang.UnsupportedOperationException;
import java.util.Iterator;

public class SingleElementInfiniteIterator<T>
	implements Iterator<T> {
	private final T element;

	public SingleElementInfiniteIterator(T element) {
		this.element = element;
	}

	public boolean hasNext() {
		return true;
	}

	public T next() {
		return element;
	}

	public void remove() {
		throw new UnsupportedOperationException("Cannot remove from this iterator.");
	}
}
