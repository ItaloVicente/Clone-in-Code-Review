package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;

import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class IndexDiffFilter extends TreeFilter {
	private final int dirCache;

	private final int workingTree;

	private final boolean honorIgnores;

	public IndexDiffFilter(int dirCacheIndex
		this(dirCacheIndex
	}

	public IndexDiffFilter(int dirCacheIndex
			boolean honorIgnores) {
		this.dirCache = dirCacheIndex;
		this.workingTree = workingTreeIndex;
		this.honorIgnores = honorIgnores;
	}

	@Override
	public boolean include(TreeWalk tw) throws MissingObjectException
			IncorrectObjectTypeException
		final int wm = tw.getRawMode(workingTree);
		if (wm == 0)
			return true;

		final int cnt = tw.getTreeCount();
		final int dm = tw.getRawMode(dirCache);
		if (dm == 0) {
			if (honorIgnores && workingTree(tw).isEntryIgnored()) {
				int i = 0;
				for (; i < cnt; i++) {
					if (i == dirCache || i == workingTree)
						continue;
					if (tw.getRawMode(i) != 0)
						break;
				}

				return i == cnt ? false : true;
			} else {
				return true;
			}
		}

		if (tw.isSubtree())
			return true;

		for (int i = 0; i < cnt; i++) {
			if (i == dirCache || i == workingTree)
				continue;
			if (tw.getRawMode(i) != dm || !tw.idEqual(i
				return true;
		}

		WorkingTreeIterator wi = workingTree(tw);
		DirCacheIterator di = tw.getTree(dirCache
		return wi.isModified(di.getDirCacheEntry()
	}

	private WorkingTreeIterator workingTree(TreeWalk tw) {
		return tw.getTree(workingTree
	}

	@Override
	public boolean shouldBeRecursive() {
		return true;
	}

	@Override
	public TreeFilter clone() {
		return this;
	}

	@Override
	public String toString() {
		return "INDEX_DIFF_FILTER";
	}
}
