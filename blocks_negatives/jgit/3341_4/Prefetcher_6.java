	synchronized void setCacheLoadedChunks(boolean cacheLoadedChunks) {
		this.cacheLoadedChunks = cacheLoadedChunks;
	}

	void push(DhtReader ctx, Collection<RevCommit> roots) throws DhtException,
			MissingObjectException {
