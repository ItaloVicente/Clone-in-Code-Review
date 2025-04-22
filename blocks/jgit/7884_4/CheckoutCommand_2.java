
	public static enum Stage {
		BASE(DirCacheEntry.STAGE_1)

		OURS(DirCacheEntry.STAGE_2)

		THEIRS(DirCacheEntry.STAGE_3);

		private final int number;

		private Stage(int number) {
			this.number = number;
		}
	}

