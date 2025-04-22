package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.TestRepository;

public class PedestrianObjectReachabilityTest
		extends ObjectReachabilityTestCase {

	@Override
	ObjectReachabilityChecker getChecker(
			TestRepository<FileRepository> repository)
			throws Exception {
		return new PedestrianObjectReachabilityChecker(
				repository.getRevWalk().toObjectWalkWithSameObjects());
	}
}
