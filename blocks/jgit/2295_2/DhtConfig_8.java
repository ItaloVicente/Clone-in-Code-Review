
package org.eclipse.jgit.storage.dht;

import java.lang.ref.SoftReference;

final class DeltaBaseCache {
	private static final SoftReference<Entry> dead = new SoftReference<Entry>(
			null);

	private int maxByteCount;

	private final Slot[] cache;

	private Slot lruHead;

	private Slot lruTail;

	private int openByteCount;

	DeltaBaseCache(DhtReaderOptions options) {
		maxByteCount = options.getDeltaBaseCacheLimit();

		final int cacheSize = options.getDeltaBaseCacheSize();
		cache = new Slot[cacheSize];
		for (int i = 0; i < cacheSize; i++)
			cache[i] = new Slot();
	}

	Entry get(ChunkKey key
		Slot e = cache[hash(position)];
		if (e.position == position && key.equals(e.provider)) {
			Entry buf = e.data.get();
			if (buf != null) {
				moveToHead(e);
				return buf;
			}
		}
		return null;
	}

	void put(ChunkKey key
		if (data.length > maxByteCount)

		final Slot e = cache[hash(position)];
		clearEntry(e);

		openByteCount += data.length;
		releaseMemory();

		e.provider = key;
		e.position = position;
		e.sz = data.length;
		e.data = new SoftReference<Entry>(new Entry(data
		moveToHead(e);
	}

	private void releaseMemory() {
		while (openByteCount > maxByteCount && lruTail != null) {
			Slot currOldest = lruTail;
			Slot nextOldest = currOldest.lruPrev;

			clearEntry(currOldest);
			currOldest.lruPrev = null;
			currOldest.lruNext = null;

			if (nextOldest == null)
				lruHead = null;
			else
				nextOldest.lruNext = null;
			lruTail = nextOldest;
		}
	}

	private void moveToHead(final Slot e) {
		unlink(e);
		e.lruPrev = null;
		e.lruNext = lruHead;
		if (lruHead != null)
			lruHead.lruPrev = e;
		else
			lruTail = e;
		lruHead = e;
	}

	private void unlink(final Slot e) {
		final Slot prev = e.lruPrev;
		final Slot next = e.lruNext;
		if (prev != null)
			prev.lruNext = next;
		if (next != null)
			next.lruPrev = prev;
	}

	private void clearEntry(final Slot e) {
		openByteCount -= e.sz;
		e.provider = null;
		e.data = dead;
		e.sz = 0;
	}

	private static int hash(int position) {
		return (position << 22) >>> 22;
	}

	static class Entry {
		final byte[] data;

		final int type;

		Entry(final byte[] aData
			data = aData;
			type = aType;
		}
	}

	private static class Slot {
		Slot lruPrev;

		Slot lruNext;

		ChunkKey provider;

		int position;

		int sz;

		SoftReference<Entry> data = dead;
	}
}
