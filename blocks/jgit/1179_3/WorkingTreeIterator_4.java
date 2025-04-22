	private long determineCanonicalizedLength(InputStream raw)
			throws IOException {
		long length = 0;

		final EolCanonicalizingInputStream is = new EolCanonicalizingInputStream(
				raw);
		for (int read = 0; read != -1; read = is.read(contentReadBuffer)) {
			length += read;
		}

		return length;
	}

	private byte[] calculateHash(InputStream is
			throws IOException {
		contentDigest.reset();
		contentDigest.update(hblob);
		contentDigest.update((byte) ' ');

		long sz = length;
		if (sz == 0) {
			contentDigest.update((byte) '0');
		} else {
			final int bufn = contentReadBuffer.length;
			int p = bufn;
			do {
				contentReadBuffer[--p] = digits[(int) (sz % 10)];
				sz /= 10;
			} while (sz > 0);
			contentDigest.update(contentReadBuffer
		}
		contentDigest.update((byte) 0);

		for (;;) {
			final int r = is.read(contentReadBuffer);
			if (r <= 0)
				break;
			contentDigest.update(contentReadBuffer
			sz += r;
		}
		if (sz != length)
			return zeroid;
		return contentDigest.digest();
	}

