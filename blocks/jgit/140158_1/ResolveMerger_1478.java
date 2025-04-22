
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.NoMergeBaseException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class RecursiveMerger extends ResolveMerger {
	public final int MAX_BASES = 200;

	protected RecursiveMerger(Repository local
		super(local
	}

	protected RecursiveMerger(Repository local) {
		this(local
	}

	protected RecursiveMerger(ObjectInserter inserter
		super(inserter
	}

	@Override
	protected RevCommit getBaseCommit(RevCommit a
			throws IncorrectObjectTypeException
		return getBaseCommit(a
	}

	protected RevCommit getBaseCommit(RevCommit a
			throws IOException {
		ArrayList<RevCommit> baseCommits = new ArrayList<>();
		walk.reset();
		walk.setRevFilter(RevFilter.MERGE_BASE);
		walk.markStart(a);
		walk.markStart(b);
		RevCommit c;
		while ((c = walk.next()) != null)
			baseCommits.add(c);

		if (baseCommits.isEmpty())
			return null;
		if (baseCommits.size() == 1)
			return baseCommits.get(0);
		if (baseCommits.size() >= MAX_BASES)
			throw new NoMergeBaseException(NoMergeBaseException.MergeBaseFailureReason.TOO_MANY_MERGE_BASES
					JGitText.get().mergeRecursiveTooManyMergeBasesFor
					Integer.valueOf(MAX_BASES)
							Integer.valueOf(baseCommits.size())));

		RevCommit currentBase = baseCommits.get(0);
		DirCache oldDircache = dircache;
		boolean oldIncore = inCore;
		WorkingTreeIterator oldWTreeIt = workingTreeIterator;
		workingTreeIterator = null;
		try {
			dircache = DirCache.read(reader
			inCore = true;

			List<RevCommit> parents = new ArrayList<>();
			parents.add(currentBase);
			for (int commitIdx = 1; commitIdx < baseCommits.size(); commitIdx++) {
				RevCommit nextBase = baseCommits.get(commitIdx);
				if (commitIdx >= MAX_BASES)
					throw new NoMergeBaseException(
							NoMergeBaseException.MergeBaseFailureReason.TOO_MANY_MERGE_BASES
							MessageFormat.format(
							JGitText.get().mergeRecursiveTooManyMergeBasesFor
							Integer.valueOf(MAX_BASES)
									Integer.valueOf(baseCommits.size())));
				parents.add(nextBase);
				RevCommit bc = getBaseCommit(currentBase
						callDepth + 1);
				AbstractTreeIterator bcTree = (bc == null) ? new EmptyTreeIterator()
						: openTree(bc.getTree());
				if (mergeTrees(bcTree
						nextBase.getTree()
					currentBase = createCommitForTree(resultTree
				else
					throw new NoMergeBaseException(
							NoMergeBaseException.MergeBaseFailureReason.CONFLICTS_DURING_MERGE_BASE_CALCULATION
							MessageFormat.format(
									JGitText.get().mergeRecursiveConflictsWhenMergingCommonAncestors
									currentBase.getName()
			}
		} finally {
			inCore = oldIncore;
			dircache = oldDircache;
			workingTreeIterator = oldWTreeIt;
			toBeCheckedOut.clear();
			toBeDeleted.clear();
			modifiedFiles.clear();
			unmergedPaths.clear();
			mergeResults.clear();
			failingPaths.clear();
		}
		return currentBase;
	}

	private RevCommit createCommitForTree(ObjectId tree
			throws IOException {
		CommitBuilder c = new CommitBuilder();
		c.setTreeId(tree);
		c.setParentIds(parents);
		c.setAuthor(mockAuthor(parents));
		c.setCommitter(c.getAuthor());
		return RevCommit.parse(walk
	}

	private static PersonIdent mockAuthor(List<RevCommit> parents) {
		String name = RecursiveMerger.class.getSimpleName();
		int time = 0;
		for (RevCommit p : parents)
			time = Math.max(time
		return new PersonIdent(
				name
				new Date((time + 1) * 1000L)
	}
}
