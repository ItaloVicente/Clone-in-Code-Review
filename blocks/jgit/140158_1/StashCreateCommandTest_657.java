package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.StashApplyFailureException;
import org.eclipse.jgit.events.ChangeRecorder;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StashApplyCommandTest extends RepositoryTestCase {

	private static final String PATH = "file.txt";

	private RevCommit head;

	private Git git;

	private File committedFile;

	private ChangeRecorder recorder;

	private ListenerHandle handle;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = Git.wrap(db);
		recorder = new ChangeRecorder();
		handle = db.getListenerList().addWorkingTreeModifiedListener(recorder);
		committedFile = writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		head = git.commit().setMessage("add file").call();
		assertNotNull(head);
		recorder.assertNoEvent();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (handle != null) {
			handle.remove();
		}
		super.tearDown();
	}

	@Test
	public void workingDirectoryDelete() throws Exception {
		deleteTrashFile(PATH);
		assertFalse(committedFile.exists());
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { PATH }

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertFalse(committedFile.exists());
		recorder.assertEvent(ChangeRecorder.EMPTY

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().isEmpty());
		assertTrue(status.getRemoved().isEmpty());

		assertEquals(1
		assertTrue(status.getMissing().contains(PATH));
	}

	@Test
	public void indexAdd() throws Exception {
		String addedPath = "file2.txt";
		File addedFile = writeTrashFile(addedPath
		git.add().addFilepattern(addedPath).call();

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertFalse(addedFile.exists());
		recorder.assertEvent(ChangeRecorder.EMPTY

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertTrue(addedFile.exists());
		assertEquals("content2"
		recorder.assertEvent(new String[] { addedPath }

		Status status = git.status().call();
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getAdded().contains(addedPath));
	}

	@Test
	public void indexDelete() throws Exception {
		git.rm().addFilepattern("file.txt").call();
		recorder.assertEvent(ChangeRecorder.EMPTY

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { "file.txt" }

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertFalse(committedFile.exists());
		recorder.assertEvent(ChangeRecorder.EMPTY

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getRemoved().contains(PATH));
	}

	@Test
	public void workingDirectoryModify() throws Exception {
		writeTrashFile("file.txt"

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { "file.txt" }

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"
		recorder.assertEvent(new String[] { "file.txt" }

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getModified().contains(PATH));
	}

	@Test
	public void workingDirectoryModifyInSubfolder() throws Exception {
		String path = "d1/d2/f.txt";
		File subfolderFile = writeTrashFile(path
		git.add().addFilepattern(path).call();
		head = git.commit().setMessage("add file").call();
		recorder.assertNoEvent();

		writeTrashFile(path

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { "d1/d2/f.txt" }
				ChangeRecorder.EMPTY);

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"
		recorder.assertEvent(new String[] { "d1/d2/f.txt" }
				ChangeRecorder.EMPTY);

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getModified().contains(path));
	}

	@Test
	public void workingDirectoryModifyIndexChanged() throws Exception {
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		writeTrashFile("file.txt"

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { "file.txt" }

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content3"
		recorder.assertEvent(new String[] { "file.txt" }

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getChanged().contains(PATH));
		assertEquals(1
		assertTrue(status.getModified().contains(PATH));
	}

	@Test
	public void workingDirectoryCleanIndexModify() throws Exception {
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		writeTrashFile("file.txt"

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { "file.txt" }

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"
		recorder.assertEvent(new String[] { "file.txt" }

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getChanged().contains(PATH));
	}

	@Test
	public void workingDirectoryDeleteIndexAdd() throws Exception {
		String path = "file2.txt";
		File added = writeTrashFile(path
		assertTrue(added.exists());
		git.add().addFilepattern(path).call();
		FileUtils.delete(added);
		assertFalse(added.exists());

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertFalse(added.exists());
		recorder.assertNoEvent();

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"
		recorder.assertEvent(new String[] { path }

		Status status = git.status().call();
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getAdded().contains(path));
	}

	@Test
	public void workingDirectoryDeleteIndexEdit() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		FileUtils.delete(committedFile);
		assertFalse(committedFile.exists());

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		recorder.assertEvent(new String[] { PATH }

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertFalse(committedFile.exists());
		recorder.assertEvent(ChangeRecorder.EMPTY

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertEquals(1
		assertTrue(status.getChanged().contains(PATH));
		assertTrue(status.getConflicting().isEmpty());
		assertEquals(1
		assertTrue(status.getMissing().contains(PATH));
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertTrue(status.getRemoved().isEmpty());
	}

	@Test
	public void multipleEdits() throws Exception {
		String addedPath = "file2.txt";
		git.rm().addFilepattern(PATH).call();
		File addedFile = writeTrashFile(addedPath
		git.add().addFilepattern(addedPath).call();

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertTrue(committedFile.exists());
		assertFalse(addedFile.exists());
		recorder.assertEvent(new String[] { PATH }
				new String[] { "file2.txt" });

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		recorder.assertEvent(new String[] { "file2.txt" }
				new String[] { PATH });

		Status status = git.status().call();
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getRemoved().contains(PATH));
		assertEquals(1
		assertTrue(status.getAdded().contains(addedPath));
	}

	@Test
	public void workingDirectoryContentConflict() throws Exception {
		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH

		try {
			git.stashApply().call();
			fail("Exception not thrown");
		} catch (StashApplyFailureException e) {
 		}
		assertEquals("content3"
		recorder.assertNoEvent();
	}

	@Test
	public void stashedContentMerge() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nhead change\nmore content\n"
				read(committedFile));
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();
		recorder.assertNoEvent();

		try {
			git.stashApply().call();
			fail("Expected conflict");
		} catch (StashApplyFailureException e) {
		}
		recorder.assertEvent(new String[] { PATH }
		Status status = new StatusCommand(db).call();
		assertEquals(1
		assertEquals(
				"content\n<<<<<<< HEAD\n=======\nstashed change\n>>>>>>> stash\nmore content\ncommitted change\n"
				read(PATH));
	}

	@Test
	public void stashedApplyOnOtherBranch() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();
		String path2 = "file2.txt";
		File file2 = writeTrashFile(path2
		git.add().addFilepattern(PATH).call();
		git.add().addFilepattern(path2).call();
		git.commit().setMessage("even content").call();

		String otherBranch = "otherBranch";
		git.branchCreate().setName(otherBranch).call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();
		recorder.assertNoEvent();

		git.checkout().setName(otherBranch).call();
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even more content").call();
		recorder.assertNoEvent();

		writeTrashFile(path2

		RevCommit stashed = git.stashCreate().call();

		assertNotNull(stashed);
		assertEquals("content\nmore content\n"
		assertEquals("otherBranch content"
				read(committedFile));
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { path2 }

		git.checkout().setName("master").call();
		recorder.assertEvent(new String[] { PATH }
		git.stashApply().call();
		assertEquals("content\nstashed change\nmore content\n"
		assertEquals("master content"
				read(committedFile));
		recorder.assertEvent(new String[] { path2 }
	}

	@Test
	public void stashedApplyOnOtherBranchWithStagedChange() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();
		String path2 = "file2.txt";
		File file2 = writeTrashFile(path2
		git.add().addFilepattern(PATH).call();
		git.add().addFilepattern(path2).call();
		git.commit().setMessage("even content").call();

		String otherBranch = "otherBranch";
		git.branchCreate().setName(otherBranch).call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();
		recorder.assertNoEvent();

		git.checkout().setName(otherBranch).call();
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even more content").call();
		recorder.assertNoEvent();

		writeTrashFile(path2
				"content\nstashed change in index\nmore content\n");
		git.add().addFilepattern(path2).call();
		writeTrashFile(path2

		RevCommit stashed = git.stashCreate().call();

		assertNotNull(stashed);
		assertEquals("content\nmore content\n"
		assertEquals("otherBranch content"
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { path2 }

		git.checkout().setName("master").call();
		recorder.assertEvent(new String[] { PATH }
		git.stashApply().call();
		assertEquals("content\nstashed change\nmore content\n"
		assertEquals(
				"[file.txt
						+ "[file2.txt
				indexState(CONTENT));
		assertEquals("master content"
		recorder.assertEvent(new String[] { path2 }
	}

	@Test
	public void workingDirectoryContentMerge() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();
		recorder.assertNoEvent();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nmore content\n"
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();
		recorder.assertNoEvent();

		git.stashApply().call();
		assertEquals(
				"content\nstashed change\nmore content\ncommitted change\n"
				read(committedFile));
		recorder.assertEvent(new String[] { PATH }
	}

	@Test
	public void indexContentConflict() throws Exception {
		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		writeTrashFile(PATH

		try {
			git.stashApply().call();
			fail("Exception not thrown");
		} catch (StashApplyFailureException e) {
		}
		recorder.assertNoEvent();
		assertEquals("content2"
	}

	@Test
	public void workingDirectoryEditPreCommit() throws Exception {
		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		String path2 = "file2.txt";
		writeTrashFile(path2
		git.add().addFilepattern(path2).call();
		assertNotNull(git.commit().setMessage("adding file").call());

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		recorder.assertEvent(new String[] { PATH }

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getModified().contains(PATH));
	}

	@Test
	public void stashChangeInANewSubdirectory() throws Exception {
		String subdir = "subdir";
		String fname = "file2.txt";
		String path = subdir + "/" + fname;
		String otherBranch = "otherbranch";

		writeTrashFile(subdir

		git.add().addFilepattern(path).call();
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(ChangeRecorder.EMPTY
				new String[] { subdir

		git.branchCreate().setName(otherBranch).call();
		git.checkout().setName(otherBranch).call();

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		recorder.assertEvent(new String[] { path }

		Status status = git.status().call();
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getAdded().contains(path));
	}

	@Test
	public void unstashNonStashCommit() throws Exception {
		try {
			git.stashApply().setStashRef(head.name()).call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertEquals(MessageFormat.format(
					JGitText.get().stashCommitIncorrectNumberOfParents
					head.name()
					e.getMessage());
		}
	}

	@Test
	public void unstashNoHead() throws Exception {
		Repository repo = createWorkRepository();
		try {
			Git.wrap(repo).stashApply().call();
			fail("Exception not thrown");
		} catch (NoHeadException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void noStashedCommits() throws Exception {
		try {
			git.stashApply().call();
			fail("Exception not thrown");
		} catch (InvalidRefNameException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testApplyStashWithDeletedFile() throws Exception {
		File file = writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("x").call();
		file.delete();
		git.rm().addFilepattern("file").call();
		recorder.assertNoEvent();
		git.stashCreate().call();
		recorder.assertEvent(new String[] { "file" }
		file.delete();

		git.stashApply().setStashRef("stash@{0}").call();

		assertFalse(file.exists());
		recorder.assertEvent(ChangeRecorder.EMPTY
	}

	@Test
	public void untrackedFileNotIncluded() throws Exception {
		String untrackedPath = "untracked.txt";
		File untrackedFile = writeTrashFile(untrackedPath
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.stashCreate().call();
		assertTrue(untrackedFile.exists());
		recorder.assertEvent(new String[] { PATH }

		git.stashApply().setStashRef("stash@{0}").call();
		assertTrue(untrackedFile.exists());
		recorder.assertEvent(new String[] { PATH }

		Status status = git.status().call();
		assertEquals(1
		assertTrue(status.getUntracked().contains(untrackedPath));
		assertEquals(1
		assertTrue(status.getChanged().contains(PATH));
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getModified().isEmpty());
	}

	@Test
	public void untrackedFileIncluded() throws Exception {
		String path = "a/b/untracked.txt";
		File untrackedFile = writeTrashFile(path
		RevCommit stashedCommit = git.stashCreate().setIncludeUntracked(true)
				.call();
		assertNotNull(stashedCommit);
		assertFalse(untrackedFile.exists());
		recorder.assertEvent(ChangeRecorder.EMPTY


		git.stashApply().setStashRef("stash@{0}").call();
		assertTrue(untrackedFile.exists());
		assertEquals("content"
		recorder.assertEvent(new String[] { path }

		Status status = git.status().call();
		assertEquals(1
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().contains(path));
	}

	@Test
	public void untrackedFileConflictsWithCommit() throws Exception {
		String path = "untracked.txt";
		writeTrashFile(path
		git.stashCreate().setIncludeUntracked(true).call();
		recorder.assertEvent(ChangeRecorder.EMPTY

		writeTrashFile(path
		head = git.commit().setMessage("add file").call();
		git.add().addFilepattern(path).call();
		git.commit().setMessage("conflicting commit").call();

		try {
			git.stashApply().setStashRef("stash@{0}").call();
			fail("StashApplyFailureException should be thrown.");
		} catch (StashApplyFailureException e) {
			assertEquals(e.getMessage()
		}
		assertEquals("committed"
		recorder.assertNoEvent();
	}

	@Test
	public void untrackedFileConflictsWithWorkingDirectory()
			throws Exception {
		String path = "untracked.txt";
		writeTrashFile(path
		git.stashCreate().setIncludeUntracked(true).call();
		recorder.assertEvent(ChangeRecorder.EMPTY

		writeTrashFile(path
		try {
			git.stashApply().setStashRef("stash@{0}").call();
			fail("StashApplyFailureException should be thrown.");
		} catch (StashApplyFailureException e) {
			assertEquals(e.getMessage()
		}
		assertEquals("working-directory"
		recorder.assertNoEvent();
	}

	@Test
	public void untrackedAndTrackedChanges() throws Exception {
		writeTrashFile(PATH
		String path = "untracked.txt";
		writeTrashFile(path
		git.stashCreate().setIncludeUntracked(true).call();
		assertTrue(PATH + " should exist"
		assertEquals(PATH + " should have been reset"
		assertFalse(path + " should not exist"
		recorder.assertEvent(new String[] { PATH }
		git.stashApply().setStashRef("stash@{0}").call();
		assertTrue(PATH + " should exist"
		assertEquals(PATH + " should have new content"
		assertTrue(path + " should exist"
		assertEquals(path + " should have new content"
				read(path));
		recorder.assertEvent(new String[] { PATH
	}
}
