	final void copyRawData(final PackedObjectLoader loader,
			final OutputStream out, final byte buf[], final WindowCursor curs)
			throws IOException {
		final long objectOffset = loader.objectOffset;
		final long dataOffset = objectOffset + loader.headerSize;
		final long sz = findEndOffset(objectOffset) - dataOffset;
		final PackIndex idx = idx();
