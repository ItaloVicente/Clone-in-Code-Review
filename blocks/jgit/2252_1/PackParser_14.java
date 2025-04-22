	protected byte[] buffer() {
		return tempBuffer;
	}

	protected abstract void onStoreStream(byte[] raw
			throws IOException;

	protected abstract void onPackFooter(byte[] hash) throws IOException;

	protected abstract boolean onAppendBase(int typeCode
			PackedObjectInfo info) throws IOException;

	protected abstract void onEndThinPack() throws IOException;

	protected abstract ObjectTypeAndSize seekDatabase(long databasePosition
			ObjectTypeAndSize info)
			throws IOException;

	protected abstract int readDatabase(byte[] dst
			throws IOException;

	protected abstract void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException;

	protected abstract long onEndWholeObject() throws IOException;

	protected abstract void onBeginOfsDelta(long deltaStreamPosition
			long baseStreamPosition

	protected abstract long onEndOfsDelta() throws IOException;

	protected abstract void onBeginRefDelta(long deltaStreamPosition
			AnyObjectId baseId

	protected abstract long onEndRefDelta() throws IOException;

	protected abstract void onEndObject(PackedObjectInfo obj)
			throws IOException;

	public static class ObjectTypeAndSize {
		public int type;

		public long size;
	}

