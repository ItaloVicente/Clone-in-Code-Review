		DfsBlockCache.Ref<PackBitmapIndex> idxref = bitmapIndex;
		if (idxref != null) {
			PackBitmapIndex bmidx = idxref.get();
			if (bmidx != null) {
				return bmidx;
			}
