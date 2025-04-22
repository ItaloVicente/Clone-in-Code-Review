		RefList.Builder<Ref> all = new RefList.Builder<>();
		lock.lock();
		try {
			Reftable table = reader();
			try (RefCursor rc = ALL.equals(prefix) ? table.allRefs()
							: table.seekRef(prefix))) {
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
