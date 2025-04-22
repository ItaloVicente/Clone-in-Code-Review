package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import org.eclipse.jgit.blame.Line;
import org.eclipse.jgit.blame.LineComparator;
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
		git.commit().setMessage("create file").call();

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
			assertEquals(0
			assertEquals(0
			assertEquals(1
			assertEquals(i
			assertEquals(i
			assertEquals(-1
			assertEquals(lines[i]
		}

	}

	@Test
	public void testTwoRevisions() throws Exception {
		Git git = new Git(db);

		String[] lines1 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		String[] lines2 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		LineHistoryCommand command = new LineHistoryCommand(db);
		command.setFilePath("file.txt");
		List<Revision> revisions = command.call();
		assertNotNull(revisions);
		assertEquals(2

		Set<Line> sorted = LineComparator.sort(revisions);
		assertNotNull(sorted);
		assertEquals(3
		int number = 0;
		for (Line line : sorted) {
			assertNotNull(line);
			if (number == 2) {
				assertEquals(1
				assertEquals(1
			} else {
				assertEquals(2
				assertEquals(0
			}
			assertEquals(1
			number++;
		}
	}

	@Test
	public void testLatest() throws Exception {
		Git git = new Git(db);

		String[] lines1 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		String[] lines2 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit latest = git.commit().setMessage("create file").call();

		LineHistoryCommand command = new LineHistoryCommand(db);
		command.setFilePath("file.txt");
		command.setLatestOnly(true);
		List<Revision> revisions = command.call();
		assertNotNull(revisions);
		assertEquals(1
		Revision revision = revisions.get(0);
		assertNotNull(revision);
		assertEquals(latest
		assertEquals(3

		int number = 0;
		for (Line line : revision.getLines()) {
			assertNotNull(line);
			if (number == 2) {
				assertEquals(1
				assertEquals(1
			} else {
				assertEquals(2
				assertEquals(0
			}
			assertEquals(1
			assertEquals(lines2[number]
			number++;
		}
	}
}
