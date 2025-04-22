	public static final byte[] readFully(final InputStream in
			throws IOException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(sizeHint);
		final byte[] buf = new byte[2048];
		for (;;) {
			final int read = in.read(buf
			if (read == -1) {
				return bos.toByteArray();
			}
			bos.write(buf
		}
	}

