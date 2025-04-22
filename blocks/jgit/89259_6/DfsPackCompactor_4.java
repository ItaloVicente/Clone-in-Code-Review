	private long estimatePackSize() {
		long size = 32;
		for (DfsPackFile pack : srcPacks) {
			size += pack.getPackDescription().getFileSize(PACK) - 32;
		}
		return size;
	}

