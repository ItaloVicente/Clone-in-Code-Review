		return pathCompare(name, 0, name.length, mode, //
				bBuf, bPos, bLen, bMode);
	}

	private static int pathCompare(final byte[] aBuf, int aPos, final int aEnd,
			final FileMode aMode, final byte[] bBuf, int bPos, final int bEnd,
			final FileMode bMode) {
		while (aPos < aEnd && bPos < bEnd) {
			int cmp = (aBuf[aPos++] & 0xff) - (bBuf[bPos++] & 0xff);
			if (cmp != 0)
				return cmp;
		}

		if (aPos < aEnd)
			return (aBuf[aPos] & 0xff) - lastPathChar(bMode);
		if (bPos < bEnd)
			return lastPathChar(aMode) - (bBuf[bPos] & 0xff);
		return 0;
	}

	private static int lastPathChar(final FileMode mode) {
		return FileMode.TREE.equals(mode.getBits()) ? '/' : '\0';
