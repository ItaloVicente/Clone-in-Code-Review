	private static class StashDiffFilter extends TreeFilter {

		@Override
		public boolean include(final TreeWalk walker) {
			final int m = walker.getRawMode(0);
			if (walker.getRawMode(1) != m || !walker.idEqual(1
				return true;
			if (walker.getRawMode(2) != m || !walker.idEqual(2
				return true;
			return false;
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
			return "STASH_DIFF";
		}
	}

