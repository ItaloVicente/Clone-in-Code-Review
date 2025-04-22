		List<Ref> all = new ArrayList<>();
		lock.lock();
		try {
			Reftable table = reader();
			try (RefCursor rc = ALL.equals(prefix) ? table.allRefs()
					: table.seekRefsWithPrefix(prefix)) {
				while (rc.next()) {
					Ref ref = table.resolve(rc.getRef());
					if (ref != null && ref.getObjectId() != null) {
						all.add(ref);
					}
				}
			}
		} finally {
			lock.unlock();
		}
