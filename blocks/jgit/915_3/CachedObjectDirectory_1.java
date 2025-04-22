	public ObjectLoader openObject(final WindowCursor curs
			final AnyObjectId objectId) throws IOException {
		return openObjectImpl1(curs
	}

	@Override
	ObjectLoader openObject1(WindowCursor curs
			throws IOException {
		if (unpackedObjects.contains(objectId))
			return wrapped.openObject2(curs
		return wrapped.openObject1(curs
	}

	@Override
	boolean hasObject2(String objectId) {
		throw new UnsupportedOperationException();
	}

	@Override
	ObjectLoader openObject2(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		throw new UnsupportedOperationException();
