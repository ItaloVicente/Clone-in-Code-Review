package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;

class BitmappedObjectReachabilityChecker
		implements ObjectReachabilityChecker {

	private final ObjectWalk walk;

	public BitmappedObjectReachabilityChecker(ObjectWalk walk) {
		this.walk = walk;
	}

	@Override
	public Optional<RevObject> areAllReachable(Collection<RevObject> targets
			Stream<RevObject> starters) throws IOException {

		try {
			List<RevObject> remainingTargets = new ArrayList<>(targets);
			BitmapWalker bitmapWalker = new BitmapWalker(walk
					walk.getObjectReader().getBitmapIndex()

			Iterator<RevObject> starterIt = starters.iterator();
			BitmapBuilder seen = null;
			while (starterIt.hasNext()) {
				List<RevObject> asList = Arrays.asList(starterIt.next());
				BitmapBuilder visited = bitmapWalker.findObjects(asList
						true);
				seen = seen == null ? visited : seen.or(visited);

				remainingTargets.removeIf(seen::contains);
				if (remainingTargets.isEmpty()) {
					return Optional.empty();
				}
			}

			return Optional.of(remainingTargets.get(0));
		} catch (MissingObjectException | IncorrectObjectTypeException e) {
			throw new IllegalStateException(e);
		}
	}
}
