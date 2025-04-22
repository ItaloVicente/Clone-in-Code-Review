	public Collection<PackedObjectLoader> openObjectInAllPacks(
			final AnyObjectId objectId, final WindowCursor curs)
			throws IOException {
		Collection<PackedObjectLoader> result = new LinkedList<PackedObjectLoader>();
		openObjectInAllPacks(objectId, result, curs);
		return result;
	}
