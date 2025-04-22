	/** @return bytes cached in memory for this pack, excluding the index. */
	public long getCachedSize() {
		return key.cachedSize.get();
	}

	String getPackName() {
		return packDesc.getFileName(PACK);
	}

	void setBlockSize(int newSize) {
		blockSize = newSize;
	}

