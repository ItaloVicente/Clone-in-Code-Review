	protected int objectSize;

	protected byte[] cachedBytes;

	PackedObjectLoader(final PackFile pr, final long objectOffset,
			final int headerSize) {
		pack = pr;
		this.objectOffset = objectOffset;
		this.headerSize = headerSize;
