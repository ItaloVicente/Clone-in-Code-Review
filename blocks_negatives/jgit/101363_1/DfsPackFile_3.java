	long alignToBlock(long pos) {
		int size = blockSize;
		if (size == 0)
			size = cache.getBlockSize();
		return (pos / size) * size;
	}

