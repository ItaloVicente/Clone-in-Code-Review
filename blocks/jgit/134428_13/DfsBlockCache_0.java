	<T> Ref<T> getOrLoadRef(DfsStreamKey key
			throws IOException {
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1
		if (ref != null) {
			getStat(statHit
			return ref;
		}

		ReentrantLock regionLock = lockForRef(key);
		long lockStart = System.currentTimeMillis();
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2
				if (ref != null) {
					getStat(statHit
					return ref;
				}
			}

			if (refLockWaitTime != null) {
				refLockWaitTime.accept(
						Long.valueOf(System.currentTimeMillis() - lockStart));
			}
			getStat(statMiss
			ref = loader.load();
			ref.hot = true;
			reserveSpace(ref.size
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				}
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}
		return ref;
	}

