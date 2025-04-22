	static final Comparator<DirCacheEntry> ENT_CMP = new Comparator<DirCacheEntry>() {
		@Override
		public int compare(DirCacheEntry o1, DirCacheEntry o2) {
			final int cr = cmp(o1, o2);
			if (cr != 0)
				return cr;
			return o1.getStage() - o2.getStage();
		}
	};
