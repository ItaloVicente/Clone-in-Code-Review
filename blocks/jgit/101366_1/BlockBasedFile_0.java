	int blockSize() {
		int sz = blockSize;
		if (sz == 0) {
			sz = cache.getBlockSize();
			blockSize = sz;
		}
		return sz;
