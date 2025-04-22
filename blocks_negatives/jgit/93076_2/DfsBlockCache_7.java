		reserveSpace(size);
		ReentrantLock regionLock = lockFor(key, pos);
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2, key, pos);
				if (ref != null) {
					creditSpace(size);
					return ref;
				}
			}
