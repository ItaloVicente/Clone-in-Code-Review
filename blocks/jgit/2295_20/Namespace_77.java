
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.Collection;
import java.util.Map;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.StreamingCallback;

public interface CacheService {
	void get(Collection<CacheKey> keys
			AsyncCallback<Map<CacheKey

	void modify(Collection<Change> changes

	public static class Change {
		public static enum Type {
			PUT

			PUT_IF_ABSENT

			REMOVE;
		}

		public static Change put(CacheKey key
			return new Change(Type.PUT
		}

		public static Change putIfAbsent(CacheKey key
			return new Change(Type.PUT_IF_ABSENT
		}

		public static Change remove(CacheKey key) {
			return new Change(Type.REMOVE
		}

		private final Type type;

		private final CacheKey key;

		private final byte[] data;

		public Change(Type type
			this.type = type;
			this.key = key;
			this.data = data;
		}

		public Type getType() {
			return type;
		}

		public CacheKey getKey() {
			return key;
		}

		public byte[] getData() {
			return data;
		}

		@Override
		public String toString() {
			return getType() + " " + getKey();
		}
	}
}
