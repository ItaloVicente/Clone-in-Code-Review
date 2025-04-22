			PackIndex idx = idx(ctx);
			DfsStreamKey revKey = new DfsStreamKey.ForReverseIndex(
					desc.getStreamKey(INDEX));
			revref = cache.getOrLoadRef(revKey
				PackReverseIndex revidx = new PackReverseIndex(idx);
				int sz = (int) Math.min(idx.getObjectCount() * 8
						Integer.MAX_VALUE);
				return new DfsBlockCache.Ref<>(revKey
			});
			PackReverseIndex revidx = revref.get();
			if (revidx != null) {
				reverseIndex = revref;
			}
			return revidx;
		}
	}

	PackBitmapIndex getBitmapIndex(DfsReader ctx) throws IOException {
		if (invalid || isGarbage() || !desc.hasFileExt(BITMAP_INDEX))
			return null;

		DfsBlockCache.Ref<PackBitmapIndex> idxref = bitmapIndex;
		if (idxref != null) {
			PackBitmapIndex bmidx = idxref.get();
			if (bmidx != null)
				return bmidx;
		}

		synchronized (initLock) {
			idxref = bitmapIndex;
			if (idxref != null) {
				PackBitmapIndex bmidx = idxref.get();
				if (bmidx != null)
					return bmidx;
