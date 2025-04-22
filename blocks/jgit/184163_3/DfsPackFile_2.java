					() -> {
						cacheHit.set(false);
						return loadReverseIdx(ctx
					});
			if (cacheHit.get()) {
				ctx.stats.ridxCacheHit++;
			}
