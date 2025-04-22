	private ReentrantLock lockRef(DfsStreamKey key) {
		Integer lockKey = Integer.valueOf(key.hash);
		SoftReference<ReentrantLock> ref = refLocks.get(lockKey);
		ReentrantLock lock = null;
		if (ref != null && (lock = ref.get()) != null) {
			return lock;
		}
		lock = new ReentrantLock();
		ref = new SoftReference<>(lock);
		synchronized (refLocks) {
			refLocks.put(lockKey
		}
		return lock;
	}

