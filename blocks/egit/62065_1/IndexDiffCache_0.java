	public void forget(@NonNull Repository repository) {
		synchronized (entries) {
			IndexDiffCacheEntry cacheEntry = entries.remove(repository);
			if (cacheEntry != null) {
				cacheEntry.dispose();
			}
		}
	}

