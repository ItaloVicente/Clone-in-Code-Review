
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class TreeRevFilter extends RevFilter {
	private static final int PARSED = RevWalk.PARSED;

	private static final int UNINTERESTING = RevWalk.UNINTERESTING;

	private final int rewriteFlag;
	private final TreeWalk pathFilter;

	public TreeRevFilter(RevWalk walker
		this(walker
	}


	TreeRevFilter(final RevWalk walker
			final int rewriteFlag) {
		pathFilter = new TreeWalk(walker.reader);
		pathFilter.setFilter(t);
		pathFilter.setRecursive(t.shouldBeRecursive());
		this.rewriteFlag = rewriteFlag;
	}

	@Override
	public RevFilter clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean include(RevWalk walker
			throws StopWalkException
			IncorrectObjectTypeException
		final RevCommit[] pList = c.parents;
		final int nParents = pList.length;
		final TreeWalk tw = pathFilter;
		final ObjectId[] trees = new ObjectId[nParents + 1];
		for (int i = 0; i < nParents; i++) {
			final RevCommit p = c.parents[i];
			if ((p.flags & PARSED) == 0)
				p.parseHeaders(walker);
			trees[i] = p.getTree();
		}
		trees[nParents] = c.getTree();
		tw.reset(trees);

		if (nParents == 1) {
			int chgs = 0
			while (tw.next()) {
				chgs++;
				if (tw.getRawMode(0) == 0 && tw.getRawMode(1) != 0)
					adds++;
				else
			}

			if (chgs == 0) {
				c.flags |= rewriteFlag;
				return false;
			} else {
				if (adds > 0 && tw.getFilter() instanceof FollowFilter) {
					updateFollowFilter(trees
				}
				return true;
			}
		} else if (nParents == 0) {
			if (tw.next())
				return true;
			c.flags |= rewriteFlag;
			return false;
		}

		final int[] chgs = new int[nParents];
		final int[] adds = new int[nParents];
		while (tw.next()) {
			final int myMode = tw.getRawMode(nParents);
			for (int i = 0; i < nParents; i++) {
				final int pMode = tw.getRawMode(i);
				if (myMode == pMode && tw.idEqual(i
					continue;

				chgs[i]++;
				if (pMode == 0 && myMode != 0)
					adds[i]++;
			}
		}

		boolean same = false;
		boolean diff = false;
		for (int i = 0; i < nParents; i++) {
			if (chgs[i] == 0) {

				final RevCommit p = pList[i];
				if ((p.flags & UNINTERESTING) != 0) {
					same = true;
					continue;
				}

				c.flags |= rewriteFlag;
				c.parents = new RevCommit[] { p };
				return false;
			}

			if (chgs[i] == adds[i]) {
				pList[i].parents = RevCommit.NO_PARENTS;
			}

			diff = true;
		}

		if (diff && !same) {
			return true;
		}

		c.flags |= rewriteFlag;
		return false;
	}

	@Override
	public boolean requiresCommitBody() {
		return false;
	}

	private void updateFollowFilter(ObjectId[] trees
			throws MissingObjectException
			CorruptObjectException
		TreeWalk tw = pathFilter;
		FollowFilter oldFilter = (FollowFilter) tw.getFilter();
		tw.setFilter(TreeFilter.ANY_DIFF);
		tw.reset(trees);

		List<DiffEntry> files = DiffEntry.scan(tw);
		RenameDetector rd = new RenameDetector(tw.getObjectReader()
		rd.addAll(files);
		files = rd.compute();

		TreeFilter newFilter = oldFilter;
		for (DiffEntry ent : files) {
			if (isRename(ent) && ent.getNewPath().equals(oldFilter.getPath())) {
				newFilter = FollowFilter.create(ent.getOldPath()
				RenameCallback callback = oldFilter.getRenameCallback();
				if (callback != null) {
					callback.renamed(ent);
					((FollowFilter) newFilter).setRenameCallback(callback);
				}
				break;
			}
		}
		tw.setFilter(newFilter);
	}

	private static boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == ChangeType.RENAME
				|| ent.getChangeType() == ChangeType.COPY;
	}
}
