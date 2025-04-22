				writePack(GC_REST
		}
	}

	private long estimateGcRestPackSize() {
		long size = 0;
		for (DfsPackDescription pack : getSourcePacks()) {
			switch (pack.getPackSource()) {
			case INSERT:
			case RECEIVE:
			case COMPACT:
			case GC_REST:
				size += pack.getFileSize(PACK);
				break;
			default:
				break;
			}
