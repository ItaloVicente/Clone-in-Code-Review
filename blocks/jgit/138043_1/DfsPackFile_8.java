		DfsBlockCache.Ref<PackReverseIndex> revref = reverseIndex;
		if (revref != null) {
			PackReverseIndex revidx = revref.get();
			if (revidx != null) {
				return revidx;
			}
