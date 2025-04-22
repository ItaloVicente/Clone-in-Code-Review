	PackBitmapIndexV1(final InputStream fd
			PackReverseIndex reverseIndex) throws IOException {
		super(new ObjectIdOwnerMap<StoredBitmap>());
		this.packIndex = packIndex;
		this.reverseIndex = reverseIndex;
		this.bitmaps = getBitmaps();

		final byte[] hdr = new byte[25];
		IO.readFully(fd
		final int v = NB.decodeInt32(hdr
		if (v != 1)
			throw new IOException(MessageFormat.format(
					JGitText.get().unsupportedPackIndexVersion
					Integer.valueOf(v)));
