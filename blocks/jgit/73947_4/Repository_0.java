		lastUsed.set(System.currentTimeMillis());
		if (useCnt.decrementAndGet() == 0 && !RepositoryCache.isCached(this)) {
			doClose();
		}
	}

	void evict(long maxAge) {
		LOG.debug("Called evict() for {}
				maxAge - (System.currentTimeMillis() - lastUsed.get()));
		if (useCnt.get() == 0
				&& (System.currentTimeMillis() - lastUsed.get() > maxAge)) {
			LOG.debug("evict()!");
