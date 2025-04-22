
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.lib.Constants.COMMIT_GENERATION_NOT_COMPUTED;
import static org.eclipse.jgit.lib.Constants.COMMIT_GENERATION_UNKNOWN;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.BlockList;

public class GraphCommits implements Iterable<RevCommit> {

	public static GraphCommits fromWalk(ProgressMonitor pm
			@NonNull Set<? extends ObjectId> wants
			throws IOException {
		walk.reset();
		walk.sort(RevSort.NONE);
		walk.setRetainBody(false);
		for (ObjectId id : wants) {
			RevObject o = walk.parseAny(id);
			if (o instanceof RevCommit) {
				walk.markStart((RevCommit) o);
			}
		}
		List<RevCommit> commits = new BlockList<>();
		RevCommit c;
		pm.beginTask(JGitText.get().findingCommitsForCommitGraph
				ProgressMonitor.UNKNOWN);
		while ((c = walk.next()) != null) {
			pm.update(1);
			commits.add(c);
		}
		pm.endTask();
		return new GraphCommits(pm
	}

	private static final int GENERATION_NUMBER_MAX = 0x3FFFFFFF;

	private final List<RevCommit> sortedCommits;

	private final ObjectIdOwnerMap<CommitWithPosition> commitPosMap;

	private final int extraEdgeCnt;

	private final int[] generations;

	private GraphCommits(ProgressMonitor pm
		sortedCommits = commits;
		commitPosMap = new ObjectIdOwnerMap<>();
		int cnt = 0;
		for (int i = 0; i < commits.size(); i++) {
			RevCommit c = sortedCommits.get(i);
			if (c.getParentCount() > 2) {
				cnt += c.getParentCount() - 1;
			}
			commitPosMap.add(new CommitWithPosition(c
		}
		this.extraEdgeCnt = cnt;
		this.generations = new int[size()];
		computeGenerationNumbers(pm);
	}

	private void computeGenerationNumbers(ProgressMonitor pm) {
		pm.beginTask(JGitText.get().computingCommitGeneration
		for (RevCommit cmit : this) {
			pm.update(1);
			int generation = generations[commitPosMap.get(cmit).position];
			if (generation != COMMIT_GENERATION_NOT_COMPUTED
					&& generation != COMMIT_GENERATION_UNKNOWN) {
				continue;
			}

			Stack<RevCommit> commitStack = new Stack<>();
			commitStack.push(cmit);

			while (!commitStack.empty()) {
				int maxGeneration = 0;
				boolean allParentComputed = true;
				RevCommit current = commitStack.peek();
				RevCommit parent;

				for (int i = 0; i < current.getParentCount(); i++) {
					parent = current.getParent(i);
					generation = generations[commitPosMap.get(parent).position];
					if (generation == COMMIT_GENERATION_NOT_COMPUTED
							|| generation == COMMIT_GENERATION_UNKNOWN) {
						allParentComputed = false;
						commitStack.push(parent);
						break;
					} else if (generation > maxGeneration) {
						maxGeneration = generation;
					}
				}

				if (allParentComputed) {
					RevCommit commit = commitStack.pop();
					generation = maxGeneration + 1;
					if (generation > GENERATION_NUMBER_MAX) {
						generation = GENERATION_NUMBER_MAX;
					}
					generations[commitPosMap.get(commit).position] = generation;
				}
			}
		}
		pm.endTask();
	}

	int getOidPosition(RevCommit c) throws MissingObjectException {
		CommitWithPosition commitWithPosition = commitPosMap.get(c);
		if (commitWithPosition == null) {
			throw new MissingObjectException(c
		}
		return commitWithPosition.position;
	}

	RevCommit getCommit(int oidPos) {
		if (oidPos < 0 || oidPos >= sortedCommits.size()) {
			return null;
		}
		return sortedCommits.get(oidPos);
	}

	int getGeneration(int oidPos) {
		if (oidPos < 0 || oidPos >= sortedCommits.size()) {
			return COMMIT_GENERATION_UNKNOWN;
		}
		return generations[oidPos];
	}

	int getExtraEdgeCnt() {
		return extraEdgeCnt;
	}

	int size() {
		return sortedCommits.size();
	}

	@Override
	public Iterator<RevCommit> iterator() {
		return sortedCommits.iterator();
	}

	private static class CommitWithPosition extends ObjectIdOwnerMap.Entry {

		final int position;

		CommitWithPosition(AnyObjectId id
			super(id);
			this.position = position;
		}
	}
}
