	public void merge(GitSyncCache newCache) {
		for (Entry<File, GitSyncObjectCache> entry : newCache.cache.entrySet()) {
			File key = entry.getKey();
			if (cache.containsKey(key))
				cache.get(key).merge(entry.getValue());
			else
				cache.put(key, entry.getValue());
		}
	}

