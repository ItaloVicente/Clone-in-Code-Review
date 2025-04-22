package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;

public class PedestrianObjectReachabilityChecker {
	private ObjectWalk walk;

	public PedestrianObjectReachabilityChecker(ObjectWalk walk) {
		this.walk = walk;
	}

	public Optional<RevObject> areAllReachable(Collection<RevObject> targets
			Stream<RevObject> starters) throws MissingObjectException
			IncorrectObjectTypeException
		walk.reset();
		walk.sort(RevSort.TOPO);
		for (RevObject target : targets) {
			walk.markStart(target);
		}

		Iterator<RevObject> iterator = starters.iterator();
		while (iterator.hasNext()) {
			RevObject o = iterator.next();
			walk.markUninteresting(o);

			RevObject peeled = walk.peel(o);
			if (peeled instanceof RevCommit) {
				walk.markUninteresting(((RevCommit) peeled).getTree());
			}
		}

		RevCommit commit = walk.next();
		if (commit != null) {
			return Optional.of(commit);
		}

		RevObject object = walk.nextObject();
		if (object != null) {
			return Optional.of(object);
		}

		return Optional.empty();
	}
}
