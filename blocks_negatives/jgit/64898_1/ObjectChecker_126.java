	private static int pathCompare(final byte[] raw, int aPos, final int aEnd,
			final int aMode, int bPos, final int bEnd, final int bMode) {
		while (aPos < aEnd && bPos < bEnd) {
			final int cmp = (raw[aPos++] & 0xff) - (raw[bPos++] & 0xff);
			if (cmp != 0)
				return cmp;
