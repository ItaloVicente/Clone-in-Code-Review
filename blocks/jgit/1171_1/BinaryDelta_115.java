	public static long getBaseSize(final byte[] delta) {
		int p = 0;
		long baseLen = 0;
		int c
		do {
			c = delta[p++] & 0xff;
			baseLen |= (c & 0x7f) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);
		return baseLen;
	}

	public static long getResultSize(final byte[] delta) {
		int p = 0;

		int c;
		do {
			c = delta[p++] & 0xff;
		} while ((c & 0x80) != 0);

		long resLen = 0;
		int shift = 0;
		do {
			c = delta[p++] & 0xff;
			resLen |= (c & 0x7f) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);
		return resLen;
	}
