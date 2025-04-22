		lock.lock();
		try {
			if (mergedTables == null) {
				mergedTables = new MergedReftable(stack().readers());
			}
			return mergedTables;
		} finally {
			lock.unlock();
		}
