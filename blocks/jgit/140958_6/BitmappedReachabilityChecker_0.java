package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.junit.TestRepository;

public class BitmappedReachabilityCheckerTest
		extends ReachabilityCheckerTestCase {

	@Override
	protected ReachabilityChecker getChecker(
			TestRepository<FileRepository> repository) throws Exception {
		GC gc = new GC(repo.getRepository());
		gc.setAuto(false);
		gc.gc();

		assertNotNull("Probably the test didn't define any ref"
				repo.getRevWalk().getObjectReader().getBitmapIndex());

		return new BitmappedReachabilityChecker(repository.getRevWalk());
	}

}
