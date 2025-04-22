package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class StashApplyCommandTest extends RepositoryTestCase {

	private static final String PATH = "file.txt";

	private RevCommit head;

	private Git git;

	private File committedFile;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = Git.wrap(db);
		committedFile = writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		head = git.commit().setMessage("add file").call();
		assertNotNull(head);
	}

	@Test
	public void workingDirectoryDelete() throws Exception {
		deleteTrashFile(PATH);
		assertFalse(committedFile.exists());
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertFalse(committedFile.exists());

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertFalse(addedFile.exists());

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertTrue(addedFile.exists());
		assertEquals("content2"

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertFalse(committedFile.exists());

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"

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

		writeTrashFile(path

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content3"

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertFalse(added.exists());

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertEquals("content2"

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

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed
		assertFalse(committedFile.exists());

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getRemoved().contains(PATH));
	}

	@Test
	public void multipleEdits() throws Exception {
		String addedPath = "file2.txt";
		git.rm().addFilepattern(PATH).call();
		File addedFile = writeTrashFile(addedPath
		git.add().addFilepattern(addedPath).call();

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertTrue(committedFile.exists());
		assertFalse(addedFile.exists());

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed

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
}
