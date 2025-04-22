	private DfsBlockCache.Ref<CachedIndices> loadReverseIdx(DfsReader ctx
			CachedIndices old) throws IOException {
		PackIndex idx;
		if (old != null) {
			idx = old.index;
		} else {
			idx = loadIdx(ctx).get().index;
		}
