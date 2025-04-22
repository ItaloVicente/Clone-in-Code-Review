		final PackIndexWriter iw = PackIndexWriter.createVersion(
				indexStream
		iw.write(sortByName()
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

	public void writeBitmapIndex(final OutputStream bitmapIndexStream)
			throws IOException {
		if (writeBitmaps == null)
			throw new IOException(JGitText.get().bitmapsMustBePrepared);

		long writeStart = System.currentTimeMillis();
		final PackBitmapIndexWriterV1 iw = new PackBitmapIndexWriterV1(bitmapIndexStream);
		iw.write(writeBitmaps
