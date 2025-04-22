package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

public class PedestrianReachabilityChecker implements ReachabilityChecker {

	private final boolean topoSort;

	private final RevWalk walk;

	public PedestrianReachabilityChecker(boolean topoSort
			RevWalk walk) {
		this.topoSort = topoSort;
		this.walk = walk;
	}

	@Override
	public Optional<RevCommit> areAllReachable(Collection<RevCommit> targets
			Collection<RevCommit> starters)
					throws MissingObjectException
					IOException {
		walk.reset();
		walk.setRetainBody(false);
		if (topoSort) {
			walk.sort(RevSort.TOPO);
		}

		for (RevCommit target: targets) {
			walk.markStart(target);
		}

		for (RevCommit starter : starters) {
			walk.markUninteresting(starter);
		}

		return Optional.ofNullable(walk.next());
	}
}
