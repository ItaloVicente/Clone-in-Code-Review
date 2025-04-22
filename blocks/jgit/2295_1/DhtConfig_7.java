
package org.eclipse.jgit.storage.dht;

import java.util.HashMap;
import java.util.Map;

final class DeltaBaseCache {
	private final DhtReaderOptions options;

	private final Map<KeyAndOffset

	private int size;

	private Entry lruHead;

	private Entry lruTail;

	DeltaBaseCache(DhtReaderOptions options) {
		this.options = options;
		this.map = new HashMap<KeyAndOffset
				options.getDeltaBaseCacheEntryCount());
	}

	Entry get(ChunkKey key
		Entry ent = map.get(new KeyAndOffset(key
		if (ent != null && lruHead != ent)
			moveToHead(ent);
		return ent;
	}

	void put(ChunkKey key
		int len = data.length;
		if (options.getDeltaBaseCacheSize() < len)
			return;

		while (lruTail != null && options.getDeltaBaseCacheSize() < size + len) {
			Entry t = lruTail;
			unlink(t);
			map.remove(t.key);
			size -= t.data.length;
		}

		KeyAndOffset k = new KeyAndOffset(key
		Entry ent = new Entry(k
		map.put(k
		moveToHead(ent);
		size += len;
	}

	private void moveToHead(final Entry e) {
		unlink(e);

		e.lruPrev = null;
		e.lruNext = lruHead;

		if (lruHead != null)
			lruHead.lruPrev = e;
		else
			lruTail = e;

		lruHead = e;
	}

	private void unlink(final Entry e) {
		final Entry prev = e.lruPrev;
		final Entry next = e.lruNext;

		if (prev != null)
			prev.lruNext = next;
		else
			lruHead = next;

		if (next != null)
			next.lruPrev = prev;
		else
			lruTail = prev;
	}

	static class Entry {
		final KeyAndOffset key;

		final int type;

		final byte[] data;

		private Entry lruPrev;

		private Entry lruNext;

		Entry(KeyAndOffset key
			this.key = key;
			this.type = type;
			this.data = data;
		}
	}

	private static class KeyAndOffset {
		final ChunkKey key;

		final int offset;

		KeyAndOffset(ChunkKey key
			this.key = key;
			this.offset = offset;
		}

		@Override
		public int hashCode() {
			return key.hashCode() * 31 + offset;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof KeyAndOffset) {
				KeyAndOffset o = (KeyAndOffset) obj;
				return key.equals(o.key) && offset == o.offset;
			}
			return false;
		}
	}
}
