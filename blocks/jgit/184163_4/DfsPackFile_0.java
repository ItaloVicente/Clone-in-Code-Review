						() -> {
							cacheHit.set(false);
							return loadPackIndex(ctx
						});
				if (cacheHit.get()) {
					ctx.stats.idxCacheHit++;
				}
