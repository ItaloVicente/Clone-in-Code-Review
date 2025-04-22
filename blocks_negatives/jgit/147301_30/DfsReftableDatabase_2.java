		lock.lock();
		try {
			Reftable table = reader();
			Ref ref = table.exactRef(name);
			if (ref != null && ref.isSymbolic()) {
				return table.resolve(ref);
			}
			return ref;
		} finally {
			lock.unlock();
		}
