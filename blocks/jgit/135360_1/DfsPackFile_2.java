	private DfsBlockCache.Ref<PackReverseIndex> loadReverseIdx(DfsReader ctx
			DfsStreamKey key
		PackReverseIndex revidx = new PackReverseIndex(idx);
		int sz = (int) Math.min(idx.getObjectCount() * 8
		return new DfsBlockCache.Ref<>(key
	}

