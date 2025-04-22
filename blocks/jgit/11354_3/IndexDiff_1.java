	public static enum StageState {
		BOTH_DELETED(1)

		ADDED_BY_US(2)

		DELETED_BY_THEM(3)

		ADDED_BY_THEM(4)

		DELETED_BY_US(5)

		BOTH_ADDED(6)

		BOTH_MODIFIED(7);

		private final int stageMask;

		private StageState(int stageMask) {
			this.stageMask = stageMask;
		}

		int getStageMask() {
			return stageMask;
		}

		public boolean hasBase() {
			return (stageMask & 1) != 0;
		}

		public boolean hasOurs() {
			return (stageMask & 2) != 0;
		}

		public boolean hasTheirs() {
			return (stageMask & 4) != 0;
		}

		static StageState fromMask(int stageMask) {
			switch (stageMask) {
				return BOTH_DELETED;
				return ADDED_BY_US;
				return DELETED_BY_THEM;
				return ADDED_BY_THEM;
				return DELETED_BY_US;
				return BOTH_ADDED;
				return BOTH_MODIFIED;
			default:
				return null;
			}
		}
	}

