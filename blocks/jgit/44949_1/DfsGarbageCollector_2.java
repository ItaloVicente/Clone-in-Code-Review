
	private static PackWriter.ObjectIdSet objectIdSet(final PackIndex idx) {
		return new PackWriter.ObjectIdSet() {
			public boolean contains(AnyObjectId objectId) {
				return idx.hasObject(objectId);
			}
		};
	}
