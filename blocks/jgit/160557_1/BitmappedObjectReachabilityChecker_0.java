package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.junit.TestRepository;

public class BitmappedObjectReachabilityTest
	extends ObjectReachabilityTestCase {

	@Override
	ObjectReachabilityChecker getChecker(
			TestRepository<FileRepository> repository) throws Exception {
		GC gc = new GC(repository.getRepository());
		gc.setAuto(false);
		gc.gc();

		return new BitmappedObjectReachabilityChecker(
				repository.getRevWalk().toObjectWalkWithSameObjects());
	}

}
