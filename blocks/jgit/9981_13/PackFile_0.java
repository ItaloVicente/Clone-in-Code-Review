		if (bitmapIdx == null) {
			PackIndex packIndex = idx();
			if (packIndex.hasBitmapIndex()) {
				return packIndex.getBitmapIndex(getReverseIdx());
			} else if (hasExt(BITMAP_INDEX)) {
				final PackBitmapIndex idx = PackBitmapIndex.open(
						extFile(BITMAP_INDEX)
