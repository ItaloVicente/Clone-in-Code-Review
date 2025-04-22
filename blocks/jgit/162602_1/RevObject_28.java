package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jgit.errors.MissingObjectException;

class PedestrianObjectReachabilityChecker implements ObjectReachabilityChecker {
	private final ObjectWalk walk;

	PedestrianObjectReachabilityChecker(ObjectWalk walk) {
		this.walk = walk;
	}

	@Override
	public Optional<RevObject> areAllReachable(Collection<RevObject> targets
			Stream<RevObject> starters) throws IOException {
		try {
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
		} catch (MissingObjectException | InvalidObjectException e) {
			throw new IllegalStateException(e);
		}
	}
}
