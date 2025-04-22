
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;


public class RecursiveMerger extends ResolveMerger {
	public enum RecursiveMergeFailureReason {
		DIRTY_INDEX
		DIRTY_WORKTREE
		COULD_NOT_DELETE
		TOO_MANY_BASES
		RESOLVE_RECURSION_LOOP
	}


	private final int MAX_BASES = 200;

	private int numBases = 0;

	private RevTree[] baseTrees;

	private AbstractTreeIterator[] baseTreeIterators;

	protected RecursiveMerger(Repository local
		super(local);
		SupportedAlgorithm diffAlg = local.getConfig().getEnum(
				ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_ALGORITHM
				SupportedAlgorithm.HISTOGRAM);
		mergeAlgorithm = new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
		commitNames = new String[] { "BASE"
		this.inCore = inCore;

		if (inCore) {
			dircache = DirCache.newInCore();
		}
	}

	protected RecursiveMerger(Repository local) {
		this(local
	}

	@Override
	protected boolean mergeImpl() throws IOException {
		boolean implicitDirCache = false;
		boolean clean = false;
		int callDepth = 0;

		if (dircache == null) {
			dircache = getRepository().lockDirCache();
			implicitDirCache = true;
		}

		clean = mergeRecursive(callDepth

		if (implicitDirCache) {
			dircache.unlock();
		}
		return clean;
	}

	@Override
	public boolean merge(final AnyObjectId... tips) throws IOException {
		if (tips.length > MAX_TIPS_TO_MERGE)
			return false;
		return super.merge(tips);
	}

	private String tooManyMergeBasesFor = "Too many merge bases for:\n  %s\n  %s found:\n  count %s\n  %s";

	public List<RevCommit> getBaseCommits(final int aIdx
			throws IncorrectObjectTypeException
		if (sourceCommits[aIdx] == null)
			throw new IncorrectObjectTypeException(sourceObjects[aIdx]
					Constants.TYPE_COMMIT);
		if (sourceCommits[bIdx] == null)
			throw new IncorrectObjectTypeException(sourceObjects[bIdx]
					Constants.TYPE_COMMIT);
		walk.reset();
		walk.setRevFilter(RevFilter.MERGE_BASE);
		walk.markStart(sourceCommits[aIdx]);
		walk.markStart(sourceCommits[bIdx]);

		List<RevCommit> bases = new ArrayList<RevCommit>();
		int commitIdx = 0;

		for (RevCommit base : walk) {
			if (commitIdx < MAX_BASES) {
				bases.add(base);
				commitIdx++;
			} else {
				throw new IOException(String.format(tooManyMergeBasesFor
						"TOO_MANY_BASES"
						sourceCommits[bIdx].name()
						base.name()));
			}
		}

		this.numBases = commitIdx;
		return bases;
	}

	protected AbstractTreeIterator[] mergeBases() throws IOException {
		final int numsourceTrees = sourceTrees.length;
		int commitIdx = 0;

		RevTree tempTrees[] = new RevTree[MAX_BASES];
		AbstractTreeIterator tempIterators[] = new AbstractTreeIterator[MAX_BASES];

		if (baseTrees == null) {
			baseTrees = new RevTree[MAX_BASES];
			baseTreeIterators = new AbstractTreeIterator[MAX_BASES];
		}

		for (int aIdx = 0; aIdx < numsourceTrees; aIdx++) {
			for (int bIdx = aIdx + 1; bIdx < numsourceTrees; bIdx++) {
				List<RevCommit> baseCommits = getBaseCommits(aIdx
				for (RevCommit baseCommit : baseCommits) {
					if (commitIdx < MAX_BASES) {
						tempTrees[commitIdx] = baseCommit.getTree();
						tempIterators[commitIdx] = openTree(baseCommit
								.getTree());
						commitIdx++;
					} else {
						throw new IOException(String.format(
								tooManyMergeBasesFor
								sourceCommits[aIdx].name()
								sourceCommits[bIdx].name()
								Integer.valueOf(commitIdx)
					}
				}
			}
		}

		if (commitIdx == 0) {
			baseTreeIterators[commitIdx] = new EmptyTreeIterator();
			commitIdx++;
		} else {
			baseTrees = Arrays.copyOf(tempTrees
			baseTreeIterators = Arrays.copyOf(tempIterators
		}

		return Arrays.copyOf(baseTreeIterators
	}

	protected List<RevCommit> reverseCommitList(List<RevCommit> commitList) {
		int headSize = commitList.size();
		List<RevCommit> newList = new ArrayList<RevCommit>();

		while (headSize > 0) {
			newList.add(commitList.get(headSize - 1));
			headSize--;
		}
		return newList;
	}

	List<RevCommit> commitListInsert(RevCommit item
		List<RevCommit> itemList = new ArrayList<RevCommit>();
		itemList.add(item);
		commitList.addAll(0
		return itemList.isEmpty() ? null : itemList;
	}

	List<RevCommit> getMergeBasesMany(RevCommit one
		List<RevCommit> result = new ArrayList<RevCommit>();

		return result.isEmpty() ? null : result;
	}

	List<RevCommit> getMergeBases(RevCommit one
			throws IncorrectObjectTypeException
		if (one == null)
			throw new IncorrectObjectTypeException(one
		if (two == null)
			throw new IncorrectObjectTypeException(two
		List<RevCommit> mergeBases = new ArrayList<RevCommit>();
		walk.reset();
		walk.setRevFilter(RevFilter.MERGE_BASE);
		walk.markStart(one);
		walk.markStart(two);

		int commitIdx = 0;

		for (RevCommit base : walk) {
			if (commitIdx < MAX_BASES) {
				mergeBases.add(base);
				commitIdx++;
			} else {
				throw new IOException(String.format(tooManyMergeBasesFor
						"TOO_MANY_BASES"
						Integer.valueOf(commitIdx)
			}
		}

		return mergeBases;
	}



	protected boolean mergeRecursive(int callDepth
			List<RevCommit> commonAncestors) throws IOException {
		boolean clean = false;

		List<RevCommit> mergedCommonAncestors = new ArrayList<RevCommit>();
		RevCommit firstCommonAncestor = null;
		if (commonAncestors == null) {
			commonAncestors = new ArrayList<RevCommit>();
		}

		if (commonAncestors.isEmpty()) {
			commonAncestors.addAll(reverseCommitList(getMergeBases(h1
		}

		if (!commonAncestors.isEmpty()) {
			firstCommonAncestor = commonAncestors.remove(0);
			mergedCommonAncestors.add(firstCommonAncestor);
		}

		if (commonAncestors.isEmpty()) {
			resultTree = (firstCommonAncestor == null ? null
					: firstCommonAncestor.getTree().getId());
		}

		for (RevCommit commonAncestor : commonAncestors) {
			resultTree = null;
			RevCommit saved_b1;
			RevCommit saved_b2;
			callDepth++;

			dircache.clear();
			saved_b1 = h1;
			saved_b2 = h2;

			mergeRecursive(callDepth

			if (resultTree == null) {
				throw new IOException(MessageFormat.format(
						JGitText.get().mergeRecursiveReturnedNoCommit
						callDepth
						commonAncestor.name()));
			}

			mergedCommonAncestors.add(commonAncestor);

			h1 = saved_b1;
			h2 = saved_b2;
			callDepth--;

		}

		clean = mergeTrees(resultTree

		return clean;
	}

	public int getNumBases() {
		return numBases;
	}

	public void setNumBases(int numBases) {
		this.numBases = numBases;
	}
}
