	private DfsBlockCache.Ref<CachedIndices> loadBitmapIdx(DfsReader ctx
			@Nullable CachedIndices old) throws IOException {
		PackIndex idx;
		PackReverseIndex revidx = null;
		if (old != null) {
			idx = old.index;
			revidx = old.reverseIdx;
		} else {
			idx = loadIdx(ctx).get().index;
		}
		if (revidx == null) {
			revidx = new PackReverseIndex(idx);
		}
		PackBitmapIndex bmidx;
		int sz;
