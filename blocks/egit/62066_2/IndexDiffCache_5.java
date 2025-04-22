	private void prune(Set<String> configuredRepositories) {
		synchronized (entries) {
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
				cachedEntry.dispose();
				iterator.remove();
			}
		}
	}

	@NonNull
	public Set<Repository> currentCacheEntries() {
		Set<Repository> result = null;
		synchronized (entries) {
			result = new HashSet<>(entries.keySet());
		}
		return result;
	}

