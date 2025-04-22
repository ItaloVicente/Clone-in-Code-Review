	/**
	 * Stash diff filter that looks for differences in the first three trees
	 * which must be the stash head tree, stash index tree, and stash working
	 * directory tree in any order.
	 */
	private static class StashDiffFilter extends TreeFilter {

		@Override
		public boolean include(final TreeWalk walker) {
			final int m = walker.getRawMode(0);
			if (walker.getRawMode(1) != m || !walker.idEqual(1, 0))
				return true;
			if (walker.getRawMode(2) != m || !walker.idEqual(2, 0))
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
		}
	}

