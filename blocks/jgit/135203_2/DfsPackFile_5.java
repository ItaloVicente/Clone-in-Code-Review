private DfsBlockCache.Ref<CachedIndices> loadReverseIdx(DfsReader ctx) throws IOException {
								PackIndex idx;
						if (old != null) {
							idx = old.index;
						} else {
							idx = loadIdx(ctx).index;
						}
						PackReverseIndex revidx = new PackReverseIndex(idx);
						int sz = (int) Math.min(
								idx.getObjectCount() * REC_SIZE + 8
								Integer.MAX_VALUE);
						return new DfsBlockCache.Ref<>(idxKey
								new CachedIndices(idx
