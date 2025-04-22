
package org.eclipse.jgit.storage.dfs;

import java.lang.ref.SoftReference;

final class DeltaBaseCache {
	private int maxByteCount;

	private final Slot[] table;

	private Slot lruHead;

	private Slot lruTail;

	private int curByteCount;

	DeltaBaseCache(DfsReader reader) {
		DfsReaderOptions options = reader.getOptions();
		maxByteCount = options.getDeltaBaseCacheLimit();
		table = new Slot[options.getDeltaBaseCacheSize()];
	}

	Entry get(DfsPackKey key
		Slot e = table[hash(key
		for (; e != null; e = e.tableNext) {
			if (e.offset == position && key.equals(e.pack)) {
				Entry buf = e.data.get();
				if (buf != null) {
					moveToHead(e);
					return buf;
				}
			}
		}
		return null;
	}

	void put(DfsPackKey key
		if (data.length > maxByteCount)

		curByteCount += data.length;
		releaseMemory();

		int tableIdx = hash(key
		Slot e = new Slot(key
		e.data = new SoftReference<Entry>(new Entry(data
		e.tableNext = table[tableIdx];
		table[tableIdx] = e;
		moveToHead(e);
	}

	private void releaseMemory() {
		while (curByteCount > maxByteCount && lruTail != null) {
			Slot currOldest = lruTail;
			Slot nextOldest = currOldest.lruPrev;

			curByteCount -= currOldest.size;
			unlink(currOldest);
			removeFromTable(currOldest);

			if (nextOldest == null)
				lruHead = null;
			else
				nextOldest.lruNext = null;
			lruTail = nextOldest;
		}
	}

	private void removeFromTable(Slot e) {
		int tableIdx = hash(e.pack
		Slot p = table[tableIdx];

		if (p == e) {
			table[tableIdx] = e.tableNext;
			return;
		}

		for (; p != null; p = p.tableNext) {
			if (p.tableNext == e) {
				p.tableNext = e.tableNext;
				return;
			}
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
		Slot prev = e.lruPrev;
		Slot next = e.lruNext;

		if (prev != null)
			prev.lruNext = next;
		if (next != null)
			next.lruPrev = prev;
	}

	private int hash(DfsPackKey key
		return ((int) ((key.hash + (position >> 2)) >>> 1)) % table.length;
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
		final DfsPackKey pack;

		final long offset;

		final int size;

		Slot tableNext;

		Slot lruPrev;

		Slot lruNext;

		SoftReference<Entry> data;

		Slot(DfsPackKey key
			this.pack = key;
			this.offset = offset;
			this.size = size;
		}
	}
}
