			Iterator<Map.Entry<Repository, IndexDiffCacheEntry>> iterator = entries
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Repository, IndexDiffCacheEntry> cached = iterator
						.next();
				if (configuredRepositories.contains(
						cached.getKey().getDirectory().getAbsolutePath())) {
					continue;
				}
				IndexDiffCacheEntry cachedEntry = cached.getValue();
