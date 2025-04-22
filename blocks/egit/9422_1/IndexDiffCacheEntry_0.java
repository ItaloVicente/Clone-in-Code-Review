	private static final class InterIndexDiffFilter extends TreeFilter {
		private static final int baseTree = 0;
		private static final int newTree = 1;

		@Override
		public boolean include(final TreeWalk walker) {
			final int n = walker.getTreeCount();
			if (n == 1) // Assume they meant difference to empty tree.
				return true;

			final int m = walker.getRawMode(baseTree);
			for (int i = 1; i < n; i++) {
				if (walker.getRawMode(i) != m || !walker.idEqual(i, baseTree))
					return true;
				DirCacheIterator baseDirCache = walker.getTree(baseTree, DirCacheIterator.class);
				DirCacheIterator newDirCache = walker.getTree(newTree, DirCacheIterator.class);
				if (baseDirCache != null) {
					if (newDirCache != null) {
						DirCacheEntry baseDci = baseDirCache.getDirCacheEntry();
						DirCacheEntry newDci = newDirCache.getDirCacheEntry();
						if (baseDci != null && newDci != null)
							if (baseDci.isAssumeValid() != newDci.isAssumeValid())
								return true;
					}
				}
			}
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
			return "INTERINDEX_DIFF"; //$NON-NLS-1$
		}
	}

