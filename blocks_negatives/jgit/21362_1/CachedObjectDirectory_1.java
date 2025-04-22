	boolean tryAgain1() {
		return wrapped.tryAgain1();
	}

	@Override
	public boolean has(final AnyObjectId objectId) {
		return hasObjectImpl1(objectId);
	}

	@Override
	boolean hasObject1(AnyObjectId objectId) {
		return unpackedObjects.contains(objectId)
				|| wrapped.hasObject1(objectId);
