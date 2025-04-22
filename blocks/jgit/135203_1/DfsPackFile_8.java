			DfsStreamKey idxKey = desc.getStreamKey(INDEX);
			indices = cache.getOrLoadRef(idxKey
					old -> old != null && old.reverseIdx != null
						PackIndex idx;
						if (old != null) {
							idx = old.index;
						} else {
							idx = loadIdx(ctx);
						}
						PackReverseIndex revidx = new PackReverseIndex(idx);
						int sz = (int) Math.min(
								idx.getObjectCount() * REC_SIZE + 8
								Integer.MAX_VALUE);
						return new DfsBlockCache.Ref<>(idxKey
								new CachedIndices(idx
					});
			if (indices != null) {
				cachedIndices = indices;
				return indices.reverseIdx;
