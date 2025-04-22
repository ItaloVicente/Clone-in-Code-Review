	public Consumer<Long> getRefLockWaitTimeConsumer() {
		return refLock;
	}

	public DfsBlockCacheConfig setRefLockWaitTimeConsumer(Consumer<Long> c) {
		refLock = c;
		return this;
	}

