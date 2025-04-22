	@Override
	public List<Ref> getRefsByPrefix(String prefix) throws IOException {
		List<Ref> all = new ArrayList<>();
		lock.lock();
		try {
			Reftable table = reader();
			try (RefCursor rc = ALL.equals(prefix) ? table.allRefs()
					: table.seekPrefix(prefix)) {
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

		return Collections.unmodifiableList(all);
	}

