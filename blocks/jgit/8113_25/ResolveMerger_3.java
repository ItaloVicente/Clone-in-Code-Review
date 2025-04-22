
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.NoMergeBaseException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;


public class RecursiveMerger extends ResolveMerger {
	static Logger log = Logger.getLogger(RecursiveMerger.class.toString());

	public final int MAX_BASES = 200;

	private PersonIdent ident = new PersonIdent(db);

	protected RecursiveMerger(Repository local
		super(local
	}

	protected RecursiveMerger(Repository local) {
		this(local
	}

	@Override
	protected RevCommit getBaseCommit(RevCommit a
			throws IncorrectObjectTypeException
		return getBaseCommit(a
	}

	protected RevCommit getBaseCommit(RevCommit a
			throws IOException {
		ArrayList<RevCommit> baseCommits = new ArrayList<RevCommit>();
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
			dircache = dircacheFromTree(currentBase.getTree());
			inCore = true;

			List<RevCommit> parents = new ArrayList<RevCommit>();
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
				if (mergeTrees(
						openTree(getBaseCommit(currentBase
								callDepth + 1).getTree())
						currentBase.getTree()
						nextBase.getTree()))
					currentBase = createCommitForTree(resultTree
				else
					throw new NoMergeBaseException(
							NoMergeBaseException.MergeBaseFailureReason.CONFLICTS_DURING_MERGE_BASE_CALCULATION
							MessageFormat.format(
									JGitText.get().mergeRecursiveTooManyMergeBasesFor
									Integer.valueOf(MAX_BASES)
									b.name()
									Integer.valueOf(baseCommits.size())));
			}
		} finally {
			inCore = oldIncore;
			dircache = oldDircache;
			workingTreeIterator = oldWTreeIt;
		}
		return currentBase;
	}

	private RevCommit createCommitForTree(ObjectId tree
			throws IOException {
		CommitBuilder c = new CommitBuilder();
		c.setParentIds(parents);
		c.setTreeId(tree);
		c.setAuthor(ident);
		c.setCommitter(ident);
		ObjectInserter odi = db.newObjectInserter();
		ObjectId newCommitId = odi.insert(c);
		odi.flush();
		RevCommit ret = walk.lookupCommit(newCommitId);
		walk.parseHeaders(ret);
		return ret;
	}

	private DirCache dircacheFromTree(ObjectId treeId) throws IOException {
		DirCache ret = DirCache.newInCore();
		DirCacheBuilder builder = ret.builder();
		TreeWalk tw = new TreeWalk(db);
		tw.addTree(treeId);
		tw.setRecursive(true);
		while (tw.next()) {
			DirCacheEntry e = new DirCacheEntry(tw.getRawPath());
			e.setFileMode(tw.getFileMode(0));
			e.setObjectId(tw.getObjectId(0));
			builder.add(e);
		}
		builder.finish();
		return ret;
	}
}
