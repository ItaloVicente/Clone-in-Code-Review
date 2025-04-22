				PackBitmapIndex idx = idxref.get();
				if (idx != null)
					return idx;
			}

			DfsStreamKey bitmapKey = desc.getStreamKey(BITMAP_INDEX);
			idxref = cache.getRef(bitmapKey);
			if (idxref != null) {
				PackBitmapIndex idx = idxref.get();
				if (idx != null) {
					bitmapIndex = idxref;
					return idx;
