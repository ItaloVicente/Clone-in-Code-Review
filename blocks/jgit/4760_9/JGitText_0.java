package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
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
	public void singleWorkingDirectoryDelete() throws Exception {
		deleteTrashFile("file.txt");
		RevCommit stashed = git.stashCreate().call();
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(head.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.DELETE
		assertEquals("file.txt"
	}

	@Test
	public void singleIndexAdd() throws Exception {
		File addedFile = writeTrashFile("file2.txt"
		git.add().addFilepattern("file2.txt").call();

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertFalse(addedFile.exists());
		validateStashedCommit(stashed);

		assertEquals(stashed.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.ADD
		assertEquals("file2.txt"
	}

	@Test
	public void singleIndexDelete() throws Exception {
		git.rm().addFilepattern("file.txt").call();

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(stashed.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.DELETE
		assertEquals("file.txt"
	}

	@Test
	public void singleWorkingDirectoryModify() throws Exception {
		writeTrashFile("file.txt"

		RevCommit stashed = Git.wrap(db).stashCreate().call();
		assertEquals("content"
		validateStashedCommit(stashed);

		assertEquals(head.getTree()

		List<DiffEntry> diffs = diffWorkingAgainstHead(stashed);
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
		assertEquals("file.txt"
	}

	@Test
	public void singleWorkingDirectoryModifyIndexChanged() throws Exception {
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		writeTrashFile("file.txt"

		RevCommit stashed = Git.wrap(db).stashCreate().call();
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
}
