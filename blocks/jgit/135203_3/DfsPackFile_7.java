			indices = cache.getOrLoadRef(idxKey
					old -> old != null && old.reverseIdx != null
					old -> loadReverseIdx(ctx
			if (indices != null) {
				cachedIndices = indices;
				return indices.reverseIdx;
