
package org.eclipse.jgit.storage.dht;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

final class RecentInfoCache {
	private final Map<ObjectId

	RecentInfoCache(DhtReaderOptions options) {
		final int sz = options.getRecentInfoCacheSize();
		infoCache = new LinkedHashMap<ObjectId
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry<ObjectId
				return sz < size();
			}
		};
	}

	List<ObjectInfo> get(AnyObjectId id) {
		return infoCache.get(id);
	}

	void put(AnyObjectId id
		infoCache.put(id.copy()
	}

	private static List<ObjectInfo> copyList(List<ObjectInfo> info) {
		int cnt = info.size();
		if (cnt == 1)
			return Collections.singletonList(info.get(0));

		ObjectInfo[] tmp = info.toArray(new ObjectInfo[cnt]);
		return Collections.unmodifiableList(Arrays.asList(tmp));
	}

	void clear() {
		infoCache.clear();
	}
}
