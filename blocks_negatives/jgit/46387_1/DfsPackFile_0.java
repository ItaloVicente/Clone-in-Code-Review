			int size = blockSize;
			if (size == 0) {
				size = rc.blockSize();
				if (size <= 0)
					size = cache.getBlockSize();
				else if (size < cache.getBlockSize())
					size = (cache.getBlockSize() / size) * size;
				blockSize = size;
				pos = (pos / size) * size;
			}
