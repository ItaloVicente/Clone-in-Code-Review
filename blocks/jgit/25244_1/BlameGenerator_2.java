
	private static final TreeFilter ID_DIFF = new TreeFilter() {
		@Override
		public boolean include(TreeWalk tw) {
			return !tw.idEqual(0
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
		}
	};
