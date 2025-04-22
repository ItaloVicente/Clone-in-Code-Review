		bSize = file.blockSize();
		return getOrLoad(file
	}

	private static long alignToBlock(long pos
		return (pos / bSize) * bSize;
