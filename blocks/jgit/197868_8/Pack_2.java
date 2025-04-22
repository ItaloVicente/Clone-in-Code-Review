	Boolean isBitmapModified(PackFile bitmapFile) {
		if (bitmapFile == null) {
			return bitmapFileSnapshot.isPresent();
		}

		return bitmapFileSnapshot
				.map(f -> f.isModified(bitmapFile)).orElse(true);
	}

