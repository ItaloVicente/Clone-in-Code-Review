		try {
			DfsStreamKey idxKey = desc.getStreamKey(INDEX);
			AtomicBoolean cacheHit = new AtomicBoolean(true);
			DfsBlockCache.Ref<PackIndex> idxref = cache.getOrLoadRef(idxKey
					REF_POSITION
						cacheHit.set(false);
						return loadPackIndex(ctx
					});
			if (cacheHit.get()) {
				ctx.stats.idxCacheHit++;
