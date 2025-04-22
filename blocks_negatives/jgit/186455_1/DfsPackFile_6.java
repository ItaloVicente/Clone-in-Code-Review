		synchronized (initLock) {
			if (reverseIndex != null) {
				return reverseIndex;
			}

			PackIndex idx = idx(ctx);
			DfsStreamKey revKey = new DfsStreamKey.ForReverseIndex(
					desc.getStreamKey(INDEX));
			AtomicBoolean cacheHit = new AtomicBoolean(true);
			DfsBlockCache.Ref<PackReverseIndex> revref = cache.getOrLoadRef(
					revKey,
					REF_POSITION,
					() -> {
						cacheHit.set(false);
						return loadReverseIdx(ctx, revKey, idx);
					});
			if (cacheHit.get()) {
				ctx.stats.ridxCacheHit++;
			}
			PackReverseIndex revidx = revref.get();
			if (reverseIndex == null && revidx != null) {
				reverseIndex = revidx;
			}
			return reverseIndex;
