					() -> {
						cacheHit.set(false);
						return loadBitmapIndex(ctx
					});
			if (cacheHit.get()) {
				ctx.stats.bitmapCacheHit++;
			}
