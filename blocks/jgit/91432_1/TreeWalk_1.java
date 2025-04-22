	public int isPathMatch(final byte[] p
		final AbstractTreeIterator t = currentHead;
		final byte[] c = t.path;
		final int cLen = t.pathLen;
		int ci;

		for (ci = 0; ci < cLen && ci < pLen; ci++) {
			final int c_value = (c[ci] & 0xff) - (p[ci] & 0xff);
			if (c_value != 0) {
				return 1;
			}
		}

		if (ci < cLen) {
			return c[ci] == '/' ? 0 : 1;
		}

		if (ci < pLen) {
			return p[ci] == '/' && FileMode.TREE.equals(t.mode) ? -1 : 1;
		}

		return 0;
	}

