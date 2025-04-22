
package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

public class SkipWorkTreeFilter extends TreeFilter {

	private final int treeIdx;

	public SkipWorkTreeFilter(int treeIdx) {
		this.treeIdx = treeIdx;
	}

	@Override
	public boolean include(TreeWalk walker) {
		DirCacheIterator i = walker.getTree(treeIdx
		if (i == null)
			return true;

		DirCacheEntry e = i.getDirCacheEntry();
		return e == null || !e.isSkipWorkTree();
	}

	@Override
	public boolean shouldBeRecursive() {
		return false;
	}

	@Override
	public TreeFilter clone() {
		return this;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "SkipWorkTree(" + treeIdx + ")";
	}
}
