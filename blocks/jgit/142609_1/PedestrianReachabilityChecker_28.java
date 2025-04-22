package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.NullProgressMonitor;

public class BitmappedReachabilityChecker implements ReachabilityChecker {

	private final RevWalk walk;

	public BitmappedReachabilityChecker(RevWalk walk)
			throws IOException {
		this.walk = walk;
		if (walk.getObjectReader().getBitmapIndex() == null) {
			throw new AssertionError(
		}
	}

	@Override
	public Optional<RevCommit> areAllReachable(Collection<RevCommit> targets
			Collection<RevCommit> starters) throws MissingObjectException
			IncorrectObjectTypeException
		BitmapCalculator calculator = new BitmapCalculator(walk);

		List<RevCommit> remainingTargets = new ArrayList<>(targets);
		for (RevCommit starter : starters) {
			BitmapBuilder starterBitmap = calculator.getBitmap(starter
					NullProgressMonitor.INSTANCE);
			remainingTargets.removeIf(starterBitmap::contains);
			if (remainingTargets.isEmpty()) {
				return Optional.empty();
			}
		}

		return Optional.of(remainingTargets.get(0));
	}

}
