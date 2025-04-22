	private long estimateGcPackSize(PackSource first
		EnumSet<PackSource> sourceSet = EnumSet.of(first
		long size = 32;
		for (DfsPackDescription pack : getSourcePacks()) {
			if (sourceSet.contains(pack.getPackSource())) {
				size += pack.getFileSize(PACK) - 32;
			}
		}
		return size;
	}

