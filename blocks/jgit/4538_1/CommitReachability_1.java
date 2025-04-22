package org.eclipse.jgit.revwalk;

import java.io.*;
import java.util.*;

public class CommitReachability {

	public static boolean isMergedIntoAny(final RevCommit base
			Collection<RevCommit> tipsColl
		final List<RevCommit> tipCommits = new ArrayList<RevCommit>(tipsColl);
		final List<RevCommit> startCommits = getStartCommit(tipCommits
				Collections.singletonList(base));
		final boolean[] merged = { false };
		CommonCommitLimitedRevQueue.configureRevWalk(revWalk
				new CommonCommitLimitedRevQueue.ReachabilityRegistry() {
					public void registerReachability(RevCommit startCommit
							RevCommit commit) {
						if (commit == base && tipCommits.contains(startCommit)) {
							merged[0] = true;
						}
					}
				});

		for (RevCommit commit : revWalk) {
			if (merged[0]) {
				return true;
			}
		}

		return false;
	}

	public static Set<RevCommit> getTipsThatContainsBase(final RevCommit base
			Collection<RevCommit> tipsColl
		final List<RevCommit> tipCommits = new ArrayList<RevCommit>(tipsColl);
		final List<RevCommit> startCommits = getStartCommit(tipCommits
				Collections.singletonList(base));
		final Set<RevCommit> reachableTips = new HashSet<RevCommit>();
		CommonCommitLimitedRevQueue.configureRevWalk(revWalk
				new CommonCommitLimitedRevQueue.ReachabilityRegistry() {
					public void registerReachability(RevCommit startCommit
							RevCommit commit) {
						if (commit == base && tipCommits.contains(startCommit)) {
							reachableTips.add(startCommit);
						}
					}
				});

		for (RevCommit commit : revWalk) {
		}

		return reachableTips;
	}

	public static boolean isAnyMergedInto(final Collection<RevCommit> bases
			final RevCommit tip
		final List<RevCommit> startCommits = getStartCommit(
				Collections.singletonList(tip)
		final Set<RevCommit> reachableBases = new HashSet<RevCommit>();
		final boolean[] merged = { false };
		CommonCommitLimitedRevQueue.configureRevWalk(revWalk
				new CommonCommitLimitedRevQueue.ReachabilityRegistry() {
					public void registerReachability(RevCommit startCommit
							RevCommit commit) {
						if (tip == startCommit && bases.contains(commit)) {
							merged[0] = true;
						}
					}
				});

		for (RevCommit commit : revWalk) {
			if (merged[0]) {
				return true;
			}
		}

		return false;
	}

	public static Set<RevCommit> getBasesWhichAreContainedInTip(
			final Collection<RevCommit> bases
			RevWalk revWalk) throws IOException {
		final List<RevCommit> startCommits = getStartCommit(
				Collections.singletonList(tip)
		final Set<RevCommit> reachableBases = new HashSet<RevCommit>();
		CommonCommitLimitedRevQueue.configureRevWalk(revWalk
				new CommonCommitLimitedRevQueue.ReachabilityRegistry() {
					public void registerReachability(RevCommit startCommit
							RevCommit commit) {
						if (tip == startCommit && bases.contains(commit)) {
							reachableBases.add(commit);
						}
					}
				});

		for (RevCommit commit : revWalk) {
		}

		return reachableBases;
	}

	private static List<RevCommit> getStartCommit(List<RevCommit> tipCommits
			List<RevCommit> baseCommits) {
		final List<RevCommit> startCommits = new ArrayList<RevCommit>();
		startCommits.addAll(tipCommits);
		for (RevCommit baseCommit : baseCommits) {
			if (!tipCommits.contains(baseCommit)) {
				startCommits.add(baseCommit);
			}
		}
		return startCommits;
	}
}
