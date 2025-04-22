package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.jgit.blame.Line;
import org.eclipse.jgit.blame.Revision;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class LineHistoryCommandTest extends RepositoryTestCase {

	private String join(String... lines) {
		StringBuilder joined = new StringBuilder();
		for (String line : lines)
			joined.append(line).append('\n');
		return joined.toString();
	}

	@Test
	public void testSingleRevision() throws Exception {
		Git git = new Git(db);

		String[] lines = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		LineHistoryCommand command = new LineHistoryCommand(db);
		command.setFilePath("file.txt");
		List<Revision> revisions = command.call();
		assertNotNull(revisions);
		assertEquals(1
		Revision first = revisions.get(0);
		assertNotNull(first);

		int count = first.getLineCount();
		assertEquals(count
		for (int i = 0; i < count; i++) {
			Line line = first.getLine(i);
			assertNotNull(line);
			assertEquals(commit
			assertEquals(1
			assertEquals(i
			assertEquals(lines[i]
		}

	}

	@Test
	public void testTwoRevisions() throws Exception {
		Git git = new Git(db);

		String[] lines1 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		String[] lines2 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("create file").call();

		LineHistoryCommand command = new LineHistoryCommand(db);
		command.setFilePath("file.txt");
		List<Revision> revisions = command.call();
		assertNotNull(revisions);
		assertEquals(2

		int number = 0;
		for (Line line : revisions.get(1).getLines()) {
			assertNotNull(line);
			if (number == 2) {
				assertEquals(1
				assertEquals(commit2
			} else {
				assertEquals(2
				assertEquals(commit1
			}
			number++;
		}
	}

	@Test
	public void testLatest() throws Exception {
		Git git = new Git(db);

		String[] lines1 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		String[] lines2 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("create file").call();

		LineHistoryCommand command = new LineHistoryCommand(db);
		command.setFilePath("file.txt");
		command.setLatestOnly(true);
		List<Revision> revisions = command.call();
		assertNotNull(revisions);
		assertEquals(1
		Revision revision = revisions.get(0);
		assertNotNull(revision);
		assertEquals(commit2
		assertEquals(3

		int number = 0;
		for (Line line : revision.getLines()) {
			assertNotNull(line);
			if (number == 2) {
				assertEquals(1
				assertEquals(commit2
			} else {
				assertEquals(2
				assertEquals(commit1
			}
			assertEquals(lines2[number]
			number++;
		}
	}

	@Test
	public void testRename() throws Exception {
		Git git = new Git(db);

		String[] lines1 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile("file1.txt"
		git.add().addFilepattern("file1.txt").call();
		git.rm().addFilepattern("file.txt").call();
		git.commit().setMessage("moving file").call();

		String[] lines2 = new String[] { "a"

		writeTrashFile("file1.txt"
		git.add().addFilepattern("file1.txt").call();
		RevCommit commit3 = git.commit().setMessage("editing file").call();

		LineHistoryCommand command = new LineHistoryCommand(db);
		command.setFilePath("file1.txt");
		List<Revision> revisions = command.call();
		assertNotNull(revisions);
		assertEquals(3
		assertEquals("file.txt"
		assertEquals("file1.txt"
		command.setLatestOnly(false);
		revisions = command.call();
		assertNotNull(revisions);
		assertEquals(3
		assertEquals("file.txt"
		assertEquals("file1.txt"
		assertEquals("file1.txt"
		Revision revision = revisions.get(revisions.size() - 1);
		assertEquals(commit3
		assertEquals(3

		int number = 0;
		for (Line line : revision.getLines()) {
			assertNotNull(line);
			if (number == 2) {
				assertEquals(1
				assertEquals(commit3
			} else {
				assertEquals(3
				assertEquals(commit1
			}
			assertEquals(lines2[number]
			number++;
		}
	}
}
