	Boolean isBitmapModified(PackFile bitmapFile) {
		if (!bitmapFileSnapshot.isPresent() && bitmapFile == null) {
			return false;
		}
		if(bitmapFileSnapshot.isPresent() && bitmapFile == null) {
			return true;
		}
		return bitmapFileSnapshot
				.map(f -> f.isModified(bitmapFile)).orElse(true);
	}

