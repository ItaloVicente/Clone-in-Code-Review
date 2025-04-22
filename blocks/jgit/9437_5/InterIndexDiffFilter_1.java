
package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

public final class InterIndexDiffFilter extends TreeFilter {
	private static final int baseTree = 0;

	public static final TreeFilter INSTANCE = new InterIndexDiffFilter();

	@Override
	public boolean include(final TreeWalk walker) {
		final int n = walker.getTreeCount();
			return true;

		final int m = walker.getRawMode(baseTree);
		for (int i = 1; i < n; i++) {
			DirCacheIterator baseDirCache = walker.getTree(baseTree
					DirCacheIterator.class);
			DirCacheIterator newDirCache = walker.getTree(i
					DirCacheIterator.class);
			if (baseDirCache != null && newDirCache != null) {
				DirCacheEntry baseDci = baseDirCache.getDirCacheEntry();
				DirCacheEntry newDci = newDirCache.getDirCacheEntry();
				if (baseDci != null && newDci != null) {
					if (baseDci.isAssumeValid() != newDci.isAssumeValid())
						return true;
						return false;
				}
			}
			if (walker.getRawMode(i) != m || !walker.idEqual(i
				return true;
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
	}
}
