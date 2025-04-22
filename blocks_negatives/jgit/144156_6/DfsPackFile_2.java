			DfsBlockCache.Ref<PackReverseIndex> revref = cache
					.getOrLoadRef(revKey, () -> {
						PackReverseIndex revidx = new PackReverseIndex(idx);
						int sz = (int) Math.min(idx.getObjectCount() * 8,
								Integer.MAX_VALUE);
						reverseIndex = revidx;
						return new DfsBlockCache.Ref<>(revKey, 0, sz, revidx);
					});
