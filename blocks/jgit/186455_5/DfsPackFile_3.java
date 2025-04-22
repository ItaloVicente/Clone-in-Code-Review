		DfsStreamKey bitmapKey = desc.getStreamKey(BITMAP_INDEX);
		AtomicBoolean cacheHit = new AtomicBoolean(true);
		DfsBlockCache.Ref<PackBitmapIndex> idxref = cache
				.getOrLoadRef(bitmapKey
					cacheHit.set(false);
					return loadBitmapIndex(ctx
				});
		if (cacheHit.get()) {
			ctx.stats.bitmapCacheHit++;
