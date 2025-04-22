	private static final Comparator<DirCacheTree> TREE_CMP = (DirCacheTree o1
			DirCacheTree o2) -> {
		final byte[] a = o1.encodedName;
		final byte[] b = o2.encodedName;
		final int aLen = a.length;
		final int bLen = b.length;
		int cPos;
		for (cPos = 0; cPos < aLen && cPos < bLen; cPos++) {
			final int cmp = (a[cPos] & 0xff) - (b[cPos] & 0xff);
			if (cmp != 0) {
				return cmp;
