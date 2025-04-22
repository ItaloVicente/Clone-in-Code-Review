
package org.eclipse.jgit.util.io;

final class LruCache<V> {
	private static final float LOAD_FACTOR = 0.75f;

	private Node<V>[] table;

	private int size;

	private int growAt;

	private final int maxSize;

	private Node<V> lruHead;

	private Node<V> lruTail;

	LruCache(int max) {
		table = createArray(64);
		growAt = (int) (table.length * LOAD_FACTOR);
		maxSize = max;
	}

	V get(final long key) {
		for (Node<V> n = table[index(key)]; n != null; n = n.mapNext) {
			if (n.key == key) {
				moveToHead(n);
				return n.value;
			}
		}
		return null;
	}

	V remove(final long key) {
		Node<V> n = table[index(key)];
		Node<V> prior = null;
		while (n != null) {
			if (n.key == key) {
				if (prior == null)
					table[index(key)] = n.mapNext;
				else
					prior.mapNext = n.mapNext;
				size--;

				if (n.lruPrev != null)
					n.lruPrev.lruNext = n.lruNext;
				if (n.lruNext != null)
					n.lruNext.lruPrev = n.lruPrev;

				if (lruHead == n)
					lruHead = n.lruNext;
				if (lruTail == n)
					lruTail = n.lruPrev;

				return n.value;
			}
			prior = n;
			n = n.mapNext;
		}
		return null;
	}

	V put(final long key
		for (Node<V> n = table[index(key)]; n != null; n = n.mapNext) {
			if (n.key == key) {
				final V o = n.value;
				n.value = value;
				moveToHead(n);
				return o;
			}
		}

		size++;

		if (size > maxSize && lruTail != null)
			remove(lruTail.key);
		else if (size == growAt)
			grow();

		Node<V> n = new Node<V>(key
		insert(n);
		moveToHead(n);
		return null;
	}

	int size() {
		return size;
	}

	private void moveToHead(Node<V> n) {
		if (n.lruPrev != null)
			n.lruPrev.lruNext = n.lruNext;
		if (n.lruNext != null)
			n.lruNext.lruPrev = n.lruPrev;

		n.lruPrev = null;
		n.lruNext = lruHead;

		lruHead = n;
		if (lruTail == null)
			lruTail = n;
	}

	private void insert(final Node<V> n) {
		final int idx = index(n.key);
		n.mapNext = table[idx];
		table[idx] = n;
	}

	private void grow() {
		final Node<V>[] oldTable = table;
		final int oldSize = table.length;

		table = createArray(oldSize << 1);
		growAt = (int) (table.length * LOAD_FACTOR);
		for (int i = 0; i < oldSize; i++) {
			Node<V> e = oldTable[i];
			while (e != null) {
				final Node<V> n = e.mapNext;
				insert(e);
				e = n;
			}
		}
	}

	private final int index(final long key) {
		int h = ((int) key) >>> 1;
		h ^= (h >>> 20) ^ (h >>> 12);
		return h & (table.length - 1);
	}

	@SuppressWarnings("unchecked")
	private static final <V> Node<V>[] createArray(final int sz) {
		return new Node[sz];
	}

	private static class Node<V> {
		final long key;

		V value;

		Node<V> mapNext;

		Node<V> lruPrev;

		Node<V> lruNext;

		Node(final long k
			key = k;
			value = v;
		}
	}
}
