	private static int lastPathChar(final int mode) {
		return FileMode.TREE.equals(mode) ? '/' : '\0';
	}

	private static int pathCompare(final byte[] raw, int aPos, final int aEnd,
			final int aMode, int bPos, final int bEnd, final int bMode) {
		while (aPos < aEnd && bPos < bEnd) {
			final int cmp = (raw[aPos++] & 0xff) - (raw[bPos++] & 0xff);
			if (cmp != 0)
				return cmp;
		}

		if (aPos < aEnd)
			return (raw[aPos] & 0xff) - lastPathChar(bMode);
		if (bPos < bEnd)
			return lastPathChar(aMode) - (raw[bPos] & 0xff);
		return 0;
	}

