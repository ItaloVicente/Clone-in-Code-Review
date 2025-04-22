	public Consumer<Long> getRefLockWaitTimeConsumer() {
		return refLock;
	}

	public DfsBlockCacheConfig setReflockWaitTimeConsumer(Consumer<Long> c) {
		refLock = c;
		return this;
	}

