	private ReentrantLock lockRef(DfsStreamKey key) {
		SoftReference<ReentrantLock> ref = refLocks.get(key);
		ReentrantLock lock = null;
		if (ref != null && (lock = ref.get()) != null) {
			return lock;
		}
		lock = new ReentrantLock();
		ref = new SoftReference<>(lock);
		synchronized (refLocks) {
			refLocks.put(key
		}
		return lock;
	}

