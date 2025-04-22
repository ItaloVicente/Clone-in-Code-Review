		if (bitmapIdx == null && hasExt(BITMAP_INDEX)) {
			final PackBitmapIndex idx;
			try {
				idx = PackBitmapIndex.open(packFileName.create(BITMAP_INDEX), idx(),
						getReverseIdx());
			} catch (FileNotFoundException e) {
				 invalidBitmap = true;
				 return null;
			}
