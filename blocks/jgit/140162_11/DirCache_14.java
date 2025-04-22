	static final Comparator<DirCacheEntry> ENT_CMP = (DirCacheEntry o1
			DirCacheEntry o2) -> {
		final int cr = cmp(o1
		if (cr != 0)
			return cr;
		return o1.getStage() - o2.getStage();
