package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class DirCacheCheckoutTest extends RepositoryTestCase {

	@Test
	public void testSkipConflicts() throws Exception {
		RevCommit headCommit = commitFile("a"
				"master");
		RevCommit checkoutCommit = commitFile("a"
		writeTrashFile("a"

		DirCacheCheckout dco = createDirCacheCheckout(headCommit
		dco.setFailOnConflict(false);
		dco.setSkipConflicts(true);

		boolean checkoutOk = dco.checkout();

		assertTrue(checkoutOk);
		assertEquals("a"
	}

	private DirCacheCheckout createDirCacheCheckout(RevCommit headCommit
			RevCommit checkoutCommit)
			throws IOException {
		return new DirCacheCheckout(db
				db.lockDirCache()
	}
}
