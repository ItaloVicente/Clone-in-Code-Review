	public static int compare(byte[] aPath
			byte[] bPath
		int cmp = coreCompare(
				aPath
				bPath
		if (cmp == 0) {
			cmp = lastPathChar(aMode) - lastPathChar(bMode);
		}
		return cmp;
	}

	public static int compareSameName(
			byte[] aPath
			byte[] bPath
		return coreCompare(
				aPath
				bPath
	}

	private static int coreCompare(
			byte[] aPath
			byte[] bPath
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
		if ((mode & TYPE_MASK) == TYPE_TREE) {
			return '/';
		}
		return 0;
	}

