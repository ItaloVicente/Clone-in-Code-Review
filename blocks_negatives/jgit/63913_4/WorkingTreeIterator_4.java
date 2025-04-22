		public int compare(final Entry o1, final Entry o2) {
			final byte[] a = o1.encodedName;
			final byte[] b = o2.encodedName;
			final int aLen = o1.encodedNameLen;
			final int bLen = o2.encodedNameLen;
			int cPos;

			for (cPos = 0; cPos < aLen && cPos < bLen; cPos++) {
				final int cmp = (a[cPos] & 0xff) - (b[cPos] & 0xff);
				if (cmp != 0)
					return cmp;
			}

			if (cPos < aLen)
				return (a[cPos] & 0xff) - lastPathChar(o2);
			if (cPos < bLen)
				return lastPathChar(o1) - (b[cPos] & 0xff);
			return lastPathChar(o1) - lastPathChar(o2);
