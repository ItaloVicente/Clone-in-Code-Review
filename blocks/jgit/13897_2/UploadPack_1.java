	private static Set<ObjectId> refIdSet(Collection<Ref> refs) {
		Set<ObjectId> ids = new HashSet<ObjectId>(refs.size());
		for (Ref ref : refs) {
			if (ref.getObjectId() != null)
				ids.add(ref.getObjectId());
		}
		return ids;
	}

