package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class BlameCommandTest extends RepositoryTestCase {

	private String join(String... lines) {
		StringBuilder joined = new StringBuilder();
		for (String line : lines)
			joined.append(line).append('\n');
		return joined.toString();
	}

	@Test
	public void testSingleRevision() throws Exception {
		Git git = new Git(db);

		String[] content = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertNotNull(lines);
		assertEquals(3

		for (int i = 0; i < 3; i++) {
			assertEquals(commit
			assertEquals(i
		}
	}

	@Test
	public void testTwoRevisions() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "first"
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		String[] content2 = new String[] { "first"
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("create file").call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(3

		assertEquals(commit1
		assertEquals(0

		assertEquals(commit1
		assertEquals(1

		assertEquals(commit2
		assertEquals(2
	}

	@Test
	public void testRename() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "a"
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile("file1.txt"
		git.add().addFilepattern("file1.txt").call();
		git.rm().addFilepattern("file.txt").call();
		git.commit().setMessage("moving file").call();

		String[] content2 = new String[] { "a"
		writeTrashFile("file1.txt"
		git.add().addFilepattern("file1.txt").call();
		RevCommit commit3 = git.commit().setMessage("editing file").call();

		BlameCommand command = new BlameCommand(db);
		command.setFollowFileRenames(true);
		command.setFilePath("file1.txt");
		BlameResult lines = command.call();

		assertEquals(commit1
		assertEquals(0
		assertEquals("file.txt"

		assertEquals(commit1
		assertEquals(1
		assertEquals("file.txt"

		assertEquals(commit3
		assertEquals(2
		assertEquals("file1.txt"
	}

	@Test
	public void testDeleteTraillingLines() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "a"
		String[] content2 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		BlameCommand command = new BlameCommand(db);

		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(content2.length

		for (int i = 0; i < content2.length; i++) {
			assertEquals(commit1
			assertEquals(i
		}
	}

	@Test
	public void testDeleteMiddleLines() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "a"
		String[] content2 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("edit file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		BlameCommand command = new BlameCommand(db);

		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(content2.length

		assertEquals(commit1
		assertEquals(0

		assertEquals(commit1
		assertEquals(1

		assertEquals(commit1
		assertEquals(4
	}

	@Test
	public void testEditAllLines() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "a"
		String[] content2 = new String[] { "b"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("create file").call();

		BlameCommand command = new BlameCommand(db);

		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(content2.length
		assertEquals(commit2
		assertEquals(commit2
	}

	@Test
	public void testMiddleClearAllLines() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "a"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit3 = git.commit().setMessage("edit file").call();

		BlameCommand command = new BlameCommand(db);

		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(content1.length
		assertEquals(commit3
		assertEquals(commit3
		assertEquals(commit3
	}
}
