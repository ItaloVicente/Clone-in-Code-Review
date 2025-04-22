		PackIndex idx = idx(ctx);
		DfsStreamKey revKey = new DfsStreamKey.ForReverseIndex(
				desc.getStreamKey(INDEX));
		AtomicBoolean cacheHit = new AtomicBoolean(true);
		DfsBlockCache.Ref<PackReverseIndex> revref = cache.getOrLoadRef(revKey
				REF_POSITION
					cacheHit.set(false);
					return loadReverseIdx(ctx
				});
		if (cacheHit.get()) {
			ctx.stats.ridxCacheHit++;
		}
		PackReverseIndex revidx = revref.get();
		if (reverseIndex == null && revidx != null) {
			reverseIndex = revidx;
