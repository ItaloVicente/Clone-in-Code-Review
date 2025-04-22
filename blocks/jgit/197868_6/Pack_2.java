	Boolean isBitmapModified(PackFile bitmapFile) {
		if (!bitmapFileSnapshot.isPresent() && bitmapFile == null) {
			return false;
		}
		return bitmapFileSnapshot
				.map(f -> bitmapFile == null || f.isModified(bitmapFile)).orElse(true);
	}

