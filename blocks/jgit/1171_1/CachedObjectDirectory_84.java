	public void close() {
	}

	@Override
	public ObjectInserter newInserter() {
		return wrapped.newInserter();
	}

	@Override
	public ObjectDatabase newCachedDatabase() {
		return this;
	}

	@Override
	FileObjectDatabase newCachedFileObjectDatabase() {
		return this;
	}

	@Override
	File getDirectory() {
		return wrapped.getDirectory();
	}

	@Override
	AlternateHandle[] myAlternates() {
		if (alts == null) {
			AlternateHandle[] src = wrapped.myAlternates();
			alts = new AlternateHandle[src.length];
			for (int i = 0; i < alts.length; i++) {
				FileObjectDatabase s = src[i].db;
				alts[i] = new AlternateHandle(s.newCachedFileObjectDatabase());
			}
		}
		return alts;
	}

	@Override
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
	}

	@Override
	ObjectLoader openObject(final WindowCursor curs
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
