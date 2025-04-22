	private Prefetcher prefetch(final int type
		int limit = readerOptions.getChunkLimit();
		int prefetchLimit = (int) (limit * (ratio / 100.0));
		recentChunks.setMaxBytes(limit - prefetchLimit);
		return new Prefetcher(this
	}

