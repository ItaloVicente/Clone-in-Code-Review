	private long estimatePackSize() {
		long size = 0;
		for (DfsPackFile pack : srcPacks) {
			size += pack.getPackDescription().getFileSize(PACK);
		}
		return size;
	}

