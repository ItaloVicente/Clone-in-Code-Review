	private void evict(Repository repo) {
		LOG.debug("Called evict() for {}
				20000 - (System.currentTimeMillis() - repo.closedAt.get()));
		if (repo.useCnt.get() == 0
				&& (System.currentTimeMillis() - repo.closedAt.get() > 20000)) {
			LOG.debug("evict()!");
			repo.doClose();
			unregister(repo);
		}
	}

