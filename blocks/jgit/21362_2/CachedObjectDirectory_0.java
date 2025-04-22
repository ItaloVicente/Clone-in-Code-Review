	public boolean has(final AnyObjectId objectId) throws IOException {
		if (unpackedObjects.contains(objectId))
			return true;
		if (wrapped.hasPackedObject(objectId))
			return true;
		for (CachedObjectDirectory alt : myAlternates()) {
			if (alt.has(objectId))
				return true;
		}
		return false;
