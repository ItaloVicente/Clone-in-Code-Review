package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class RebaseCommandTest extends RepositoryTestCase {
	private void createBranch(ObjectId objectId
			throws IOException {
		RefUpdate updateRef = db.updateRef(branchName);
		updateRef.setNewObjectId(objectId);
		updateRef.update();
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

	public void testFastForwardWithNewFile() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/topic");
		assertFalse(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.UP_TO_DATE
	}

	public void testConflictFreeWithSingleFile() throws Exception {
		Git git = new Git(db);

		File theFile = writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit second = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file1"
		checkFile(theFile
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in master").call();

		createBranch(second
		checkoutBranch("refs/heads/topic");
		checkFile(theFile

		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in topic").call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.OK
		checkFile(theFile
	}

	public void testAbortOnConflict() throws Exception {
		Git git = new Git(db);

		File theFile = writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit second = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file1"
		checkFile(theFile
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in master").call();

		createBranch(second
		checkoutBranch("refs/heads/topic");
		checkFile(theFile

		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in topic").call();

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in topic again").call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		checkFile(theFile
				"<<<<<<< OURS\n1master\n=======\n1topic\n>>>>>>> THEIRS\n2\n3\n");

		assertEquals(RepositoryState.REBASING_REBASING
		res = git.rebase().setAbort().call();
		assertEquals(res.getStatus()
		assertEquals("refs/heads/topic"
		checkFile(theFile
	}
}
