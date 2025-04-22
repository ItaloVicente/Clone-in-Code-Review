package org.eclipse.jgit.revwalk;

import java.io.*;
import java.util.*;

public class CommonCommitLimitedRevQueue extends DateRevQueue {

	public static CommonCommitLimitedRevQueue configureRevWalk(RevWalk revWalk
			List<RevCommit> startCommits
			ReachabilityRegistry reachabilityRegistry) throws IOException {
		final RevFlag processedFlag = revWalk
				.newFlag("CommonCommitLimitedRevQueue-processed");
		final CommonCommitLimitedRevQueue queue = new CommonCommitLimitedRevQueue(
				startCommits
		revWalk.reset();
		revWalk.queue = queue;
		revWalk.markStart(startCommits);
		revWalk.sort(RevSort.COMMIT_TIME_DESC);
		return queue;
	}

	private final List<RevCommit> startCommits;

	private final RevFlag processedFlag;

	private final Map<RevCommit

	private final ReachabilityRegistry reachabilityRegistry;

	private CommonCommitLimitedRevQueue(List<RevCommit> startCommits
			ReachabilityRegistry reachabilityRegistry
		this.startCommits = startCommits;
		this.processedFlag = processedFlag;
		this.commitToReachability = new HashMap<RevCommit
		this.reachabilityRegistry = reachabilityRegistry;

		clear();
	}

	@Override
	public void add(RevCommit c) {
		final BitSet reachability = commitToReachability.get(c);
		if (reachability == null) {
			return;
		}

		super.add(c);
	}

	@Override
	public RevCommit next() {
		final RevCommit commit = super.next();
		if (commit == null) {
			return null;
		}

		propagateReachabilityToParents(commit
		return commit;
	}

	@Override
	public void clear() {
		super.clear();

		commitToReachability.clear();

		for (int index = 0; index < startCommits.size(); index++) {
			final RevCommit commit = startCommits.get(index);
			final BitSet reachability = new BitSet(startCommits.size());
			reachability.set(index);
			commitToReachability.put(commit
			commit.flags |= processedFlag.mask;
			if (reachabilityRegistry != null) {
				reachabilityRegistry.registerReachability(commit
			}
		}
	}

	private void propagateReachabilityToParents(RevCommit commit
			List<RevCommit> startCommits) {
		final BitSet reachability = commitToReachability.remove(commit);
		if (reachability == null
				|| reachability.cardinality() == startCommits.size()) {
			return;
		}

		final RevCommit[] parents = commit.getParents();
		if (parents == null) {
			return;
		}

		for (RevCommit parent : parents) {
			final BitSet parentReachability = commitToReachability.get(parent);
			if (parentReachability == null) {
				if ((parent.flags & processedFlag.mask) != 0) {
					propagateReachabilityOfLateCommit(parent
							new HashSet<RevCommit>());
				} else {
					commitToReachability.put(parent
				}
			} else {
				final BitSet newReachability = new BitSet(startCommits.size());
				newReachability.or(reachability);
				newReachability.or(parentReachability);
				commitToReachability.put(parent
			}

			parent.flags |= processedFlag.mask;

			if (reachabilityRegistry != null) {
				for (int index = 0; index < startCommits.size(); index++) {
					if (reachability.get(index)) {
						reachabilityRegistry.registerReachability(
								startCommits.get(index)
					}
				}
			}
		}
	}

	private void propagateReachabilityOfLateCommit(RevCommit commit
			BitSet reachability
		final BitSet existingReachability = commitToReachability.get(commit);
			final BitSet newReachability = new BitSet(startCommits.size());
			newReachability.or(reachability);
			newReachability.or(existingReachability);
			commitToReachability.put(commit
			return;
		}

		if ((commit.flags & processedFlag.mask) == 0) {
			return;
		}

		for (RevCommit parentCommit : commit.getParents()) {
			if (!propagateParents.contains(parentCommit)) {
				propagateReachabilityOfLateCommit(parentCommit
						propagateParents);
			}
		}
	}

	public static interface ReachabilityRegistry {
		void registerReachability(RevCommit startCommit
	}
}
