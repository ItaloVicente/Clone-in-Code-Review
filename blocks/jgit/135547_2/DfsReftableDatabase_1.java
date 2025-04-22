	@Override
	public Set<Ref> resolveTipSha1(ObjectId id) throws IOException {
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

