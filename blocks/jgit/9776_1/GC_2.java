	private static ObjectIdSet objectIdSet(final PackIndex idx) {
		return new ObjectIdSet() {
			public boolean contains(AnyObjectId objectId) {
				return idx.hasObject(objectId);
			}
		};
	}
