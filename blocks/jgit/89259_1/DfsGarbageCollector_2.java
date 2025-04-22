	private long estimateGcPackSize() {
		long size = 0;
		for (DfsPackDescription pack : getSourcePacks()) {
			switch (pack.getPackSource()) {
			case INSERT:
			case RECEIVE:
			case COMPACT:
			case GC:
				size += pack.getFileSize(PACK);
				break;
			default:
				break;
			}
		}
		return size;
	}

