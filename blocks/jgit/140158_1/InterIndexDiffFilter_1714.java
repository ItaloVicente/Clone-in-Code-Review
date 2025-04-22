package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class IndexDiffFilter extends TreeFilter {
	private final int dirCache;

	private final int workingTree;

	private final boolean honorIgnores;

	private final Set<String> ignoredPaths = new HashSet<>();

	private final LinkedList<String> untrackedParentFolders = new LinkedList<>();

	private final LinkedList<String> untrackedFolders = new LinkedList<>();

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
		final int cnt = tw.getTreeCount();
		final int wm = tw.getRawMode(workingTree);
		WorkingTreeIterator wi = workingTree(tw);
		String path = tw.getPathString();

		DirCacheIterator di = tw.getTree(dirCache
		if (di != null) {
			DirCacheEntry dce = di.getDirCacheEntry();
			if (dce != null) {
				if (dce.isAssumeValid())
					return false;
				if (dce.getStage() != 0)
					return true;
			}
		}

		if (!tw.isPostOrderTraversal()) {
			if (FileMode.TREE.equals(wm)
					&& !(honorIgnores && wi.isEntryIgnored())) {
				copyUntrackedFolders(path);
				untrackedParentFolders.addFirst(path);
			}

			for (int i = 0; i < cnt; i++) {
				int rmode = tw.getRawMode(i);
				if (i != workingTree && rmode != FileMode.TYPE_MISSING
						&& FileMode.TREE.equals(rmode)) {
					untrackedParentFolders.clear();
					break;
				}
			}
		}

		if (wm == 0)
			return true;

		final int dm = tw.getRawMode(dirCache);
		if (dm == FileMode.TYPE_MISSING) {
			if (honorIgnores && wi.isEntryIgnored()) {
				ignoredPaths.add(wi.getEntryPathString());
				int i = 0;
				for (; i < cnt; i++) {
					if (i == dirCache || i == workingTree)
						continue;
					if (tw.getRawMode(i) != FileMode.TYPE_MISSING)
						break;
				}

				return i != cnt;
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

		return wi.isModified(di == null ? null : di.getDirCacheEntry()
				tw.getObjectReader());
	}

	private void copyUntrackedFolders(String currentPath) {
		String pathToBeSaved = null;
		while (!untrackedParentFolders.isEmpty()
				&& !currentPath.startsWith(untrackedParentFolders.getFirst()
			pathToBeSaved = untrackedParentFolders.removeFirst();
		if (pathToBeSaved != null) {
			while (!untrackedFolders.isEmpty()
					&& untrackedFolders.getLast().startsWith(pathToBeSaved))
				untrackedFolders.removeLast();
			untrackedFolders.addLast(pathToBeSaved);
		}
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
	}

	public Set<String> getIgnoredPaths() {
		return ignoredPaths;
	}

	public List<String> getUntrackedFolders() {
		LinkedList<String> ret = new LinkedList<>(untrackedFolders);
		if (!untrackedParentFolders.isEmpty()) {
			String toBeAdded = untrackedParentFolders.getLast();
			while (!ret.isEmpty() && ret.getLast().startsWith(toBeAdded))
				ret.removeLast();
			ret.addLast(toBeAdded);
		}
		return ret;
	}
}
