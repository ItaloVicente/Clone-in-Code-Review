			attrCacheClock++;
			CacheEntry entry = attrCacheByPath.get(dir);
			if (entry != null) {
				entry.lastAccessed = attrCacheClock;
				return entry.attributes;
			}
			FileStoreAttributes attrs = getFileStoreAttributes(dir);
			attrCacheByPath.put(dir
					new CacheEntry(dir
			if (attrCacheByPath.size() > attrCacheMaxSize.get()) {
				purgeAttrCacheByPath();
			}
			return attrs;
		}

		private static void purgeAttrCacheByPath() {
			synchronized (attrCacheByPath) {
				List<CacheEntry> entriesToPurge = new ArrayList<>(
						attrCacheByPath.values());
				Collections.sort(entriesToPurge
						.comparingLong(o -> -o.lastAccessed));
				for (int index = attrCachePurgeSize
						.get(); index < entriesToPurge.size(); index++) {
					attrCacheByPath.remove(entriesToPurge.get(index).path);
				}
			}
