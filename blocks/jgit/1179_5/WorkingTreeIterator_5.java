	private long computeLength(InputStream in) throws IOException {
		long length = 0;
		for (;;) {
			long n = in.skip(1 << 20);
			if (n <= 0)
				break;
			length += n;
		}
		return length;
	}

	private byte[] computeHash(InputStream in
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
			final int r = in.read(contentReadBuffer);
			if (r <= 0)
				break;
			contentDigest.update(contentReadBuffer
			sz += r;
		}
		if (sz != length)
			return zeroid;
		return contentDigest.digest();
	}

