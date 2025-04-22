			PackIndex idx = idx(ctx);
			PackReverseIndex revidx = getReverseIdx(ctx);
			DfsStreamKey bitmapKey = desc.getStreamKey(BITMAP_INDEX);
			idxref = cache.getOrLoadRef(bitmapKey,
					() -> loadBitmapIdx(ctx, bitmapKey, idx, revidx));
			PackBitmapIndex bmidx = idxref.get();
			if (bmidx != null) {
				bitmapIndex = idxref;
