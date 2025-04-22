			DfsStreamKey idxKey = desc.getStreamKey(INDEX);
			indices = cache.getOrLoadRef(idxKey
					old -> old != null && old.bitmapIdx != null
						PackIndex idx;
						PackReverseIndex revidx = null;
						if (old != null) {
							idx = old.index;
							revidx = old.reverseIdx;
						} else {
							idx = loadIdx(ctx);
