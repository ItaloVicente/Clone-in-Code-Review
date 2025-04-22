package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.WorkDirCheckout;
import org.eclipse.jgit.lib.GitIndex.Entry;
import org.eclipse.jgit.revwalk.RevCommit;

public class MergeCommandTest extends RepositoryTestCase {

	public void testMergeInItself() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();

		MergeResult result = git.merge().setCommit(db.getRef(Constants.HEAD)).call();
		assertEquals(MergeResult.MergeStatus.ALREADY_UP_TO_DATE
	}

	public void testAlreadyUpToDate() throws Exception {
		Git git = new Git(db);
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first

		RevCommit second = git.commit().setMessage("second commit").call();
		MergeResult result = git.merge().setCommit(db.getRef("refs/heads/branch1")).call();
		assertEquals(MergeResult.MergeStatus.ALREADY_UP_TO_DATE
		assertEquals(second

	}

	public void testFastForward() throws Exception {
		Git git = new Git(db);
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first

		RevCommit second = git.commit().setMessage("second commit").call();

		checkoutBranch("refs/heads/branch1");

		MergeResult result = git.merge().setCommit(db.getRef(Constants.MASTER)).call();

		assertEquals(MergeResult.MergeStatus.FAST_FORWARD
		assertEquals(second
	}

	public void testFastForwardWithFiles() throws Exception {
		Git git = new Git(db);

		addNewFileToIndex("file1");
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkDir()
		createBranch(first

		addNewFileToIndex("file2");
		RevCommit second = git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkDir()

		checkoutBranch("refs/heads/branch1");
		assertFalse(new File(db.getWorkDir()

		MergeResult result = git.merge().setCommit(db.getRef(Constants.MASTER)).call();

		assertTrue(new File(db.getWorkDir()
		assertTrue(new File(db.getWorkDir()
		assertEquals(MergeResult.MergeStatus.FAST_FORWARD
		assertEquals(second
	}

	private void createBranch(ObjectId objectId
		RefUpdate updateRef = db.updateRef(branchName);
		updateRef.setNewObjectId(objectId);
		updateRef.update();
	}

	private void checkoutBranch(String branchName) throws Exception  {
		File workDir = db.getWorkDir();
		if (workDir != null) {
			WorkDirCheckout workDirCheckout = new WorkDirCheckout(db
					workDir
					db.getIndex()
			workDirCheckout.setFailOnConflict(true);
			try {
				workDirCheckout.checkout();
			} catch (CheckoutConflictException e) {
				throw new JGitInternalException(
						"Couldn't check out because of conflicts"
			}
		}

		RefUpdate refUpdate = db.updateRef(Constants.HEAD);
		refUpdate.link(branchName);
	}

	private void addNewFileToIndex(String filename) throws IOException
			CorruptObjectException {
		File writeTrashFile = writeTrashFile(filename

		GitIndex index = db.getIndex();
		Entry entry = index.add(db.getWorkDir()
		entry.update(writeTrashFile);
		index.write();
	}
}
