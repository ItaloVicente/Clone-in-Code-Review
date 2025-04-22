package org.eclipse.jgit.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jgit.api.RebaseCommand.Operation;
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
		RevCommit lastMasterChange = git.commit().setMessage(
				"change file1 in master").call();

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
		assertEquals("refs/heads/topic"
		assertEquals(lastMasterChange
				db.resolve(Constants.HEAD)).getParent(0));
	}

	public void testFilesAddedFromTwoBranches() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit masterCommit = git.commit().setMessage("Add file1 to master")
				.call();

		createBranch(masterCommit
		checkoutBranch("refs/heads/file2");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit addFile2 = git.commit().setMessage(
				"Add file2 to branch file2").call();

		createBranch(masterCommit
		checkoutBranch("refs/heads/file3");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("Add file3 to branch file3").call();

		assertTrue(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream("refs/heads/file2").call();
		assertEquals(Status.OK

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()

		assertEquals("refs/heads/file3"
		assertEquals(addFile2
				db.resolve(Constants.HEAD)).getParent(0));

		checkoutBranch("refs/heads/file2");
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
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
		git.commit().setMessage("add a line to file1 in topic").call();

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in topic").call();

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit lastTopicCommit = git.commit().setMessage(
				"change file1 in topic again").call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		checkFile(theFile
				"<<<<<<< OURS\n1master\n=======\n1topic\n>>>>>>> THEIRS\n2\n3\n4\n");

		assertEquals(RepositoryState.REBASING_MERGE
		assertEquals(countPicks()
		res = git.rebase().setOperation(Operation.ABORT).call();
		assertEquals(res.getStatus()
		assertEquals("refs/heads/topic"
		checkFile(theFile
		RevWalk rw = new RevWalk(db);
		assertEquals(lastTopicCommit
				.parseCommit(db.resolve(Constants.HEAD)));
	}

	private int countPicks() throws IOException {
		int count = 0;
		File todoFile = new File(db.getDirectory()
				"rebase-merge/git-rebase-todo");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(todoFile)
		try {
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("pick "))
					count++;
				line = br.readLine();
			}
			return count;
		} finally {
			br.close();
		}
	}
}
