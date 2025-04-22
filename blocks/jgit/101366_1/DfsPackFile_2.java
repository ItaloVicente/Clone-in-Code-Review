	private long alignToBlock(long pos) {
		int sz = blockSize;
		if (sz == 0) {
			sz = cache.getBlockSize();
			blockSize = sz;
		}
		return (pos / sz) * sz;
	}

