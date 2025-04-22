		int sz = (int) Math.min(objCnt * recSize
		DfsBlockCache.Ref<CachedIndices> ref = cache.putRef(
				desc.getStreamKey(INDEX)
				new CachedIndices(idx
		CachedIndices indices;
		if (ref != null && (indices = ref.get()) != null) {
			cachedIndices = indices;
		}
