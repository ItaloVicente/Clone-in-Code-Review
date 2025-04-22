package org.eclipse.jgit.revwalk;

import java.io.IOException;

public class ReachabilityCheckerFactory {

	public static ReachabilityChecker getReachabilityChecker(RevWalk walk)
			throws IOException {
		if (walk.getObjectReader().getBitmapIndex() != null) {
			return new BitmappedReachabilityChecker(walk);
		}

		return new PedestrianReachabilityChecker(true
	}
}
