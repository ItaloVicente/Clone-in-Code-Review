	public void addAll(BlockList<T> src) {
		if (src.size == 0)
			return;

		int srcDirIdx = 0;
		for (; srcDirIdx < src.tailDirIdx; srcDirIdx++)
			addAll(src.directory[srcDirIdx]
		if (src.tailBlkIdx != 0)
			addAll(src.tailBlock
	}

	public void addAll(T[] src
		while (0 < srcCnt) {
			int i = tailBlkIdx;
			int n = Math.min(srcCnt
			if (n == 0) {
				add(src[srcIdx++]);
				srcCnt--;
				continue;
			}

			System.arraycopy(src
			tailBlkIdx += n;
			size += n;
			srcIdx += n;
			srcCnt -= n;
		}
	}

