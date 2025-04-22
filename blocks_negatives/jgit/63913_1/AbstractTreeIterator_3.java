		final byte[] a = path;
		final int aEnd = pathLen;

		for (; aPos < aEnd && bPos < bEnd; aPos++, bPos++) {
			final int cmp = (a[aPos] & 0xff) - (b[bPos] & 0xff);
			if (cmp != 0)
				return cmp;
		}

		if (aPos < aEnd)
			return (a[aPos] & 0xff) - lastPathChar(bMode);
		if (bPos < bEnd)
			return lastPathChar(mode) - (b[bPos] & 0xff);
		return lastPathChar(mode) - lastPathChar(bMode);
