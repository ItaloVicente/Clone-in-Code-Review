		DfsBlockCache.Ref<PackIndex> idxref = index;
		if (idxref != null) {
			PackIndex idx = idxref.get();
			if (idx != null) {
				return idx;
			}
