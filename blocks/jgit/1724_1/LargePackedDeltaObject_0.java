		DeltaStream ds = new DeltaStream(new InflaterInputStream(delta)
		if (size == SIZE_UNKNOWN)
			size = ds.getSize();
		return ds;
	}

	private SeekableInputStream openBase(final WindowCursor wc
			final ObjectId baseId) throws IOException
			FileNotFoundException {
		final File basePath = wc.db.deltaBaseCacheEntry(baseId);
		for (;;) {
			try {
				return SeekableInputStream.open(basePath);

			} catch (FileNotFoundException notFound) {
				extractBase(wc
