	@Override
	public Set<Ref> resolveTipSha1(ObjectId id) throws IOException {
		if (!getReftableConfig().isIndexObjects()) {
			return super.resolveTipSha1(id);
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

