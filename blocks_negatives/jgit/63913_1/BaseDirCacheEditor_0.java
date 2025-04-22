	static int pathCompare(byte[] aPath, int aPos, int aEnd, int aMode,
			byte[] bPath, int bPos, int bEnd, int bMode) {
		while (aPos < aEnd && bPos < bEnd) {
			int cmp = (aPath[aPos++] & 0xff) - (bPath[bPos++] & 0xff);
			if (cmp != 0) {
				return cmp;
			}
		}

		if (aPos < aEnd) {
			return (aPath[aPos] & 0xff) - lastPathChar(bMode);
		}
		if (bPos < bEnd) {
			return lastPathChar(aMode) - (bPath[bPos] & 0xff);
		}
		return 0;
	}

	private static int lastPathChar(int mode) {
		return TREE.equals(mode) ? '/' : '\0';
	}

