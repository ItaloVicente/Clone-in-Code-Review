		lastUsed.set(System.currentTimeMillis());
		useCnt.decrementAndGet();
	}

	void evict(long maxAge) {
		LOG.debug("Called evict() for {}
				maxAge - (System.currentTimeMillis() - lastUsed.get()));
		if (useCnt.get() == 0
				&& (System.currentTimeMillis() - lastUsed.get() > maxAge)) {
			LOG.debug("evict()!");
