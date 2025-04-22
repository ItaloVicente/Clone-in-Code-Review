	<T> Ref<T> getOrLoadRef(DfsStreamKey key
			throws IOException {
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1
		if (ref != null) {
			getStat(statHit
			return ref;
		}

		ReentrantLock regionLock = lockRef(key);
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

			getStat(statMiss
			ref = loader.load();
			reserveSpace(ref.size
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}
		return ref;
	}

