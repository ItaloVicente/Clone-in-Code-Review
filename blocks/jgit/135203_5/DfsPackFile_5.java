			indices = cache.getOrLoadRef(idxKey
					old -> old == null || old.bitmapIdx == null
					old -> loadBitmapIdx(ctx
			if (indices != null) {
				cachedIndices = indices;
				return indices.bitmapIdx;
