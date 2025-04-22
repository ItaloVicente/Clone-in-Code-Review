	Boolean isBitmapModified(PackFile bitmapFile) {
		if (!bitMapFileSnapshot.isPresent() && bitmapFile == null) {
			return false;
		}
		return bitMapFileSnapshot
				.map(f -> bitmapFile == null || f.isModified(bitmapFile)).orElse(true);
	}

