package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.TestRepository;

public class PedestrianReachabilityCheckerTest
		extends ReachabilityCheckerTestCase {

	@Override
	protected ReachabilityChecker getChecker(
			TestRepository<FileRepository> repository) {
		return new PedestrianReachabilityChecker(true
	}

}
