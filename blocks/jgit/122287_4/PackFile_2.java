		if (bitmapIdx == null) {
			PackFileName name = names.get(BITMAP_INDEX);
			if (name != null) {
				final PackBitmapIndex idx;
				try {
					idx = PackBitmapIndex.open(name
				} catch (FileNotFoundException e) {
					names.remove(BITMAP_INDEX);
					return null;
				}
