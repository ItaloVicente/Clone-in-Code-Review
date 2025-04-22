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
	void openObjectInAllPacks(Collection<PackedObjectLoader> out
			WindowCursor curs
		wrapped.openObjectInAllPacks(out
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
	public boolean hasObject(final AnyObjectId objectId) {
		return hasObjectImpl1(objectId);
