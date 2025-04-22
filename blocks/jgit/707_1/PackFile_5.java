	private OpenFile newOpenHandle() throws IOException {
		if (invalid)
			throw new PackInvalidException(packFile);

		final PackIndex idx = idx();
		final RandomAccessFile fd;
