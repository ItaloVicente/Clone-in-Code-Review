			attrCacheTimestamp++;
			CacheEntry entry = attrCacheByPath.get(dir);
			if (entry != null) {
				entry.lastAccessedTimestamp = attrCacheTimestamp;
				return entry.attributes;
			}
			FileStoreAttributes c = getFileStoreAttributes(dir);
			attrCacheByPath.put(dir
					new CacheEntry(dir
			if (attrCacheByPath.size() > attrCacheMaxSize.get()) {
				purgeAttrCacheByPath();
			}
			return c;
		}

		private static void purgeAttrCacheByPath() {
			synchronized (attrCacheByPath) {
				List<CacheEntry> entriesToPurge = new ArrayList<>(
						attrCacheByPath.values());
				Collections.sort(entriesToPurge
						.comparingLong(o -> -o.lastAccessedTimestamp));
				for (int index = attrCachePurgeSize
						.get(); index < entriesToPurge.size(); index++) {
					attrCacheByPath.remove(entriesToPurge.get(index).path);
				}
			}
