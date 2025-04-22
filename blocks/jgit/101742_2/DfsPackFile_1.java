
		int bs = desc.getBlockSize(PACK);
		if (bs > 0) {
			setBlockSize(bs);
		}

		long sz = desc.getFileSize(PACK);
		length = sz > 0 ? sz : -1;
