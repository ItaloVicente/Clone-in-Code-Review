	public int getBlockSize(PackExt ext) {
		int i = ext.getPosition();
		return i < blockSizeMap.length ? blockSizeMap[i] : 0;
	}

	public DfsPackDescription setBlockSize(PackExt ext
		int i = ext.getPosition();
		if (i >= blockSizeMap.length) {
			blockSizeMap = Arrays.copyOf(blockSizeMap
		}
		blockSizeMap[i] = Math.max(0
		return this;
	}

