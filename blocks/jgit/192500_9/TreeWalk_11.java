	public byte[] getRawPath(int nth) {
		final AbstractTreeIterator t = trees[nth];
		final int n = t.pathLen;
		final byte[] r = new byte[n];
		System.arraycopy(t.path
		return r;
	}

