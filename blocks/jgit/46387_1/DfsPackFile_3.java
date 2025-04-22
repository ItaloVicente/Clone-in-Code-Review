	private int blockSize(ReadableChannel rc) {
		int size = blockSize;
		if (size == 0) {
			size = rc.blockSize();
			if (size <= 0)
				size = cache.getBlockSize();
			else if (size < cache.getBlockSize())
				size = (cache.getBlockSize() / size) * size;
			blockSize = size;
		}
		return size;
	}

	private int read(ReadableChannel rc
		int n;
		do {
			n = rc.read(buf);
		} while (0 < n && buf.hasRemaining());
		return buf.position();
	}

