package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CherryPickCommandTest extends RepositoryTestCase {
	public void testCherryPick() throws IOException
			GitAPIException {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit firstCommit = git.commit().setMessage("create a").call();

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("create b").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("enlarged a").call();

		writeTrashFile("a"
				"first line\nsecond line\nthird line\nfourth line\n");
		git.add().addFilepattern("a").call();
		RevCommit fixingA = git.commit().setMessage("fixed a").call();

		git.branchCreate().setName("side").setStartPoint(firstCommit).call();
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("enhanced a").call();

		git.cherryPick().include(fixingA).call();

		assertFalse(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
				"first line\nsecond line\nthird line\nfeature++\n");
		Iterator<RevCommit> history = git.log().call().iterator();
		assertEquals("fixed a"
		assertEquals("enhanced a"
		assertEquals("create a"
		assertFalse(history.hasNext());
	}

	private void checkoutBranch(String branchName)
			throws IllegalStateException
		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		RevCommit branch = walk.parseCommit(db.resolve(branchName));
		DirCacheCheckout dco = new DirCacheCheckout(db
				db.lockDirCache()
		dco.setFailOnConflict(true);
		dco.checkout();
		walk.release();
		RefUpdate refUpdate = db.updateRef(Constants.HEAD);
		refUpdate.link(branchName);
	}

}
