package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.ReflogEntry;
import org.eclipse.jgit.storage.file.ReflogReader;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class StashCreateCommandTest extends RepositoryTestCase {

	private RevCommit head;

	private Git git;

	private File committedFile;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = Git.wrap(db);
		committedFile = writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		head = git.commit().setMessage("add file").call();
		assertNotNull(head);
	}

	private void validateStashedCommit(final RevCommit commit)
			throws IOException {
		assertNotNull(commit);
		Ref stashRef = db.getRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(commit
		assertNotNull(commit.getAuthorIdent());
		assertEquals(commit.getAuthorIdent()
		assertEquals(2

		RevWalk walk = new RevWalk(db);
		try {
			for (RevCommit parent : commit.getParents())
				walk.parseBody(parent);
		} finally {
			walk.release();
		}

		assertEquals(1
		assertEquals(head
		assertFalse("Head tree matches stashed commit tree"
				.equals(head.getTree()));
		assertEquals(head
		assertFalse(commit.getFullMessage().equals(
				commit.getParent(1).getFullMessage()));
	}

	private TreeWalk createTreeWalk() {
		TreeWalk walk = new TreeWalk(db);
		walk.setRecursive(true);
		walk.setFilter(TreeFilter.ANY_DIFF);
		return walk;
	}

	private List<DiffEntry> diffWorkingAgainstHead(final RevCommit commit)
			throws IOException {
		TreeWalk walk = createTreeWalk();
		try {
			walk.addTree(commit.getParent(0).getTree());
			walk.addTree(commit.getTree());
			return DiffEntry.scan(walk);
		} finally {
			walk.release();
		}
	}

	private List<DiffEntry> diffIndexAgainstHead(final RevCommit commit)
			throws IOException {
		TreeWalk walk = createTreeWalk();
		try {
			walk.addTree(commit.getParent(0).getTree());
			walk.addTree(commit.getParent(1).getTree());
			return DiffEntry.scan(walk);
		} finally {
			walk.release();
		}
	}

	@Test
	public void noLocalChanges() throws Exception {
		assertNull(git.stashCreate().call());
	}

	@Test
	public void workingDirectoryDelete() throws Exception {
		deleteTrashFile("file.txt");
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(head.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.DELETE
		assertEquals("file.txt"
	}

	@Test
	public void indexAdd() throws Exception {
		File addedFile = writeTrashFile("file2.txt"
		git.add().addFilepattern("file2.txt").call();

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertFalse(addedFile.exists());
		validateStashedCommit(stashed);

		assertEquals(stashed.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.ADD
		assertEquals("file2.txt"
	}

	@Test
	public void indexDelete() throws Exception {
		git.rm().addFilepattern("file.txt").call();

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(stashed.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.DELETE
		assertEquals("file.txt"
	}

	@Test
	public void workingDirectoryModify() throws Exception {
		writeTrashFile("file.txt"

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(head.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
		assertEquals("file.txt"
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
		validateStashedCommit(stashed);

		assertEquals(head.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
		assertEquals(path
	}

	@Test
	public void workingDirectoryModifyIndexChanged() throws Exception {
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		writeTrashFile("file.txt"

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		assertFalse(stashed.getTree().equals(stashed.getParent(1).getTree()));

		List<DiffEntry> workingDiffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
				.getChangeType());
		assertEquals("file.txt"

		List<DiffEntry> indexDiffs = diffIndexAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
				.getChangeType());
		assertEquals("file.txt"

		assertEquals(workingDiffs.get(0).getOldId()
				.getOldId());
		assertFalse(workingDiffs.get(0).getNewId()
				.equals(indexDiffs.get(0).getNewId()));
	}

	@Test
	public void workingDirectoryCleanIndexModify() throws Exception {
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		writeTrashFile("file.txt"

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(stashed.getParent(1).getTree()

		List<DiffEntry> workingDiffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
				.getChangeType());
		assertEquals("file.txt"

		List<DiffEntry> indexDiffs = diffIndexAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
				.getChangeType());
		assertEquals("file.txt"

		assertEquals(workingDiffs.get(0).getOldId()
				.getOldId());
		assertTrue(workingDiffs.get(0).getNewId()
				.equals(indexDiffs.get(0).getNewId()));
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

		validateStashedCommit(stashed);

		assertEquals(stashed.getParent(1).getTree()

		List<DiffEntry> workingDiffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.ADD
				.getChangeType());
		assertEquals(path

		List<DiffEntry> indexDiffs = diffIndexAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.ADD
				.getChangeType());
		assertEquals(path

		assertEquals(workingDiffs.get(0).getOldId()
				.getOldId());
		assertTrue(workingDiffs.get(0).getNewId()
				.equals(indexDiffs.get(0).getNewId()));
	}

	@Test
	public void workingDirectoryDeleteIndexEdit() throws Exception {
		File edited = writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		FileUtils.delete(edited);
		assertFalse(edited.exists());

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		assertFalse(stashed.getTree().equals(stashed.getParent(1).getTree()));

		List<DiffEntry> workingDiffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.DELETE
				.getChangeType());
		assertEquals("file.txt"

		List<DiffEntry> indexDiffs = diffIndexAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
				.getChangeType());
		assertEquals("file.txt"

		assertEquals(workingDiffs.get(0).getOldId()
				.getOldId());
		assertFalse(workingDiffs.get(0).getNewId()
				.equals(indexDiffs.get(0).getNewId()));
	}

	@Test
	public void multipleEdits() throws Exception {
		git.rm().addFilepattern("file.txt").call();
		File addedFile = writeTrashFile("file2.txt"
		git.add().addFilepattern("file2.txt").call();

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertNotNull(stashed);
		assertFalse(addedFile.exists());
		validateStashedCommit(stashed);

		assertEquals(stashed.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(2
		assertEquals(DiffEntry.ChangeType.DELETE
		assertEquals("file.txt"
		assertEquals(DiffEntry.ChangeType.ADD
		assertEquals("file2.txt"
	}

	@Test
	public void refLogIncludesCommitMessage() throws Exception {
		PersonIdent who = new PersonIdent("user"
		deleteTrashFile("file.txt");
		RevCommit stashed = git.stashCreate().setPerson(who).call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		ReflogReader reader = new ReflogReader(git.getRepository()
				Constants.R_STASH);
		ReflogEntry entry = reader.getLastEntry();
		assertNotNull(entry);
		assertEquals(ObjectId.zeroId()
		assertEquals(stashed
		assertEquals(who
		assertEquals(stashed.getFullMessage()
	}
}
