			final PackBitmapIndex idx;
			try {
				idx = PackBitmapIndex.open(extFile(BITMAP_INDEX)
						getReverseIdx());
			} catch (FileNotFoundException e) {
				 invalidBitmap = true;
				 return null;
			}
