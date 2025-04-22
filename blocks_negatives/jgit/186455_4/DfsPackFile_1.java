
			try {
				DfsStreamKey idxKey = desc.getStreamKey(INDEX);
				AtomicBoolean cacheHit = new AtomicBoolean(true);
				DfsBlockCache.Ref<PackIndex> idxref = cache.getOrLoadRef(
						idxKey,
						REF_POSITION,
						() -> {
							cacheHit.set(false);
							return loadPackIndex(ctx, idxKey);
						});
				if (cacheHit.get()) {
					ctx.stats.idxCacheHit++;
				}
				PackIndex idx = idxref.get();
				if (index == null && idx != null) {
					index = idx;
				}
				return index;
			} catch (IOException e) {
				invalid = true;
				invalidatingCause = e;
				throw e;
