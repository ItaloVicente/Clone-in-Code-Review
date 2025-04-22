		synchronized (initLock) {
			if (bitmapIndex != null) {
				return bitmapIndex;
			}

			PackIndex idx = idx(ctx);
			PackReverseIndex revidx = getReverseIdx(ctx);
			DfsStreamKey bitmapKey = desc.getStreamKey(BITMAP_INDEX);
			AtomicBoolean cacheHit = new AtomicBoolean(true);
			DfsBlockCache.Ref<PackBitmapIndex> idxref = cache.getOrLoadRef(
					bitmapKey,
					REF_POSITION,
					() -> {
						cacheHit.set(false);
						return loadBitmapIndex(ctx, bitmapKey, idx, revidx);
					});
			if (cacheHit.get()) {
				ctx.stats.bitmapCacheHit++;
			}
			PackBitmapIndex bmidx = idxref.get();
			if (bitmapIndex == null && bmidx != null) {
				bitmapIndex = bmidx;
			}
			return bitmapIndex;
