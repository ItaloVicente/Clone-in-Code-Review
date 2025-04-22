	@Override
	public Set<Ref> getTipsWithSha1(ObjectId id) throws IOException {
		if (!getReftableConfig().isIndexObjects()) {
			return super.getTipsWithSha1(id);
		}
		lock.lock();
		try {
			RefCursor cursor = reader().byObjectId(id);
			Set<Ref> refs = new HashSet<>();
			while (cursor.next()) {
				refs.add(cursor.getRef());
			}
			return refs;
		} finally {
			lock.unlock();
		}
	}

