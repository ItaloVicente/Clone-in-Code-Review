	void setBitmapIdxFile(PackFile packFile) {
		bitmapIdx = null;
		bitmapIdxFile = packFile;
		bitmapFileSnapshot = Optional.ofNullable(bitmapIdxFile).map(PackFileSnapshot::save);
	}

