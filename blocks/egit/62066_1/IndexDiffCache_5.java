	private void prune(Set<String> configuredRepositories) {
		synchronized (entries) {
			Iterator<Repository> iterator = entries.keySet().iterator();
			while (iterator.hasNext()) {
				Repository cached = iterator.next();
				if (configuredRepositories
						.contains(cached.getDirectory().getAbsolutePath())) {
					continue;
				}
				IndexDiffCacheEntry cachedEntry = entries.get(cached);
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

