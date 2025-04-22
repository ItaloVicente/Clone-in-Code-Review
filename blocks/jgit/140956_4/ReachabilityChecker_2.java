package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;

public class PedestrianReachabilityChecker implements ReachabilityChecker {

	private final boolean topoSort;

	private final RevWalk walk;

	public PedestrianReachabilityChecker(boolean topoSort
			RevWalk walk) {
		this.topoSort = topoSort;
		this.walk = walk;
	}

	@Override
	public boolean isReachable(RevCommit target
			Iterable<? extends ObjectId> starters) throws IOException {

		if (!starters.iterator().hasNext()) {
			return false;
		}
		walk.reset();
		if (topoSort) {
			walk.sort(RevSort.TOPO);
		}

		walk.markStart(target);
		for (ObjectId id : starters) {
			markUninteresting(walk
		}
		return walk.next() == null;
	}

	private static void markUninteresting(RevWalk walk
			throws IOException {
		if (id == null) {
			return;
		}
		try {
			walk.markUninteresting(walk.parseCommit(id));
		} catch (IncorrectObjectTypeException | MissingObjectException e) {
		}
	}
}
