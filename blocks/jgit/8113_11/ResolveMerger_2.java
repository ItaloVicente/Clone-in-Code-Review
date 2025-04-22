
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
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
	}

	private final int MAX_TIPS_TO_MERGE = 2;

	private final int MAX_BASES = 200;

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

		if (inCore)
			dircache = DirCache.newInCore();
	}

	protected RecursiveMerger(Repository local) {
		this(local
	}

	@Override
	protected boolean mergeImpl() throws IOException {
		boolean implicitDirCache = false;

		if (dircache == null) {
			dircache = getRepository().lockDirCache();
			implicitDirCache = true;
		}

		try {
			return mergeRecursive(0
					getBaseCommits(0
		} finally {

			if (implicitDirCache)
				dircache.unlock();
		}
	}

	@Override
	public boolean merge(final AnyObjectId... tips) throws IOException {
		if (tips.length > MAX_TIPS_TO_MERGE)
			return false;
		return super.merge(tips);
	}

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
				throw new IOException(MessageFormat.format(
						JGitText.get().mergeRecursiveTooManyMergeBasesFor
						Integer.valueOf(MAX_BASES)
						sourceCommits[bIdx].name()
						base.name()));
			}
		}

		return bases;
	}

	@Override
	public RevCommit getBaseCommit(int aIdx
			throws IncorrectObjectTypeException
		List<RevCommit> baseCommits = getBaseCommits(aIdx
		if (baseCommits == null || baseCommits.size() != 1)
			return null;
		return baseCommits.get(0);
	}

	private static <T> T[] copyOf(T[] original
		return (T[]) copyOf(original
	}

	private static <T
			Class<? extends T[]> newType) {
		T[] copy = ((Object) newType == (Object) Object[].class) ? (T[]) new Object[newLength]
				: (T[]) Array
						.newInstance(newType.getComponentType()
		System.arraycopy(original
				Math.min(original.length
		return copy;
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
						throw new IOException(
								MessageFormat.format(
										JGitText.get().mergeRecursiveTooManyMergeBasesFor
										Integer.valueOf(MAX_BASES)
										sourceCommits[aIdx].name()
										sourceCommits[bIdx].name()
										Integer.valueOf(commitIdx)
										baseCommit.name()));
					}
				}
			}
		}

		if (commitIdx == 0) {
			baseTreeIterators[commitIdx] = new EmptyTreeIterator();
			commitIdx++;
		} else {
			baseTrees = copyOf(tempTrees
			baseTreeIterators = copyOf(tempIterators
		}

		return copyOf(baseTreeIterators
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
				throw new IOException(MessageFormat.format(
						JGitText.get().mergeRecursiveTooManyMergeBasesFor
						Integer.valueOf(MAX_BASES)
						Integer.valueOf(commitIdx)
			}
		}

		return mergeBases;
	}

	protected boolean mergeRecursive(int callDepth
			List<RevCommit> commonAncestors) throws IOException {

		RevCommit firstCommonAncestor = null;
		if (commonAncestors == null)
			commonAncestors = new ArrayList<RevCommit>();

		if (commonAncestors.isEmpty())
			commonAncestors.addAll(reverseCommitList(getMergeBases(h1

		if (!commonAncestors.isEmpty()) {
			firstCommonAncestor = commonAncestors.remove(0);
		}

		if (commonAncestors.isEmpty()) {
			resultTree = (firstCommonAncestor == null ? null
					: firstCommonAncestor.getTree().getId());
		} else {
			for (RevCommit commonAncestor : commonAncestors) {
				resultTree = null;

				if (!mergeRecursive(callDepth + 1
						commonAncestor
					return false;
				}

				if (resultTree == null) {
					throw new IOException(MessageFormat.format(
							JGitText.get().mergeRecursiveReturnedNoCommit
							callDepth + 1
							(firstCommonAncestor == null ? "null"
									: firstCommonAncestor.name())
							commonAncestor.name()));
				}
			}
		}

		return mergeTrees(openTree(resultTree)
	}
}
