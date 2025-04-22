	long getRawSize(final long objOffset
			throws IOException {
		final byte[] ib = curs.tempId;
		readFully(objOffset
		int p = 0;
		int c = ib[p++] & 0xff;
		long dataSize = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = ib[p++] & 0xff;
			dataSize += (c & 0x7f) << shift;
			shift += 7;
		}
		return dataSize;
	}

