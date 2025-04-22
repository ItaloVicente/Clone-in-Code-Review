	private final byte[] decompress(final long position
			final WindowCursor curs) throws IOException
		byte[] dstbuf;
		try {
			dstbuf = new byte[sz];
		} catch (OutOfMemoryError noMemory) {
			return null;
		}

		if (curs.inflate(this
