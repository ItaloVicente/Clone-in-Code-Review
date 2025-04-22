package org.eclipse.jgit.api.blame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.junit.Test;

public class BlameGeneratorTest extends RepositoryTestCase {
	@Test
	public void testBoundLineDelete() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "first"
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();

		String[] content2 = new String[] { "third"
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("create file").call();

		BlameGenerator generator = new BlameGenerator(db
		try {
			assertEquals(3

			assertTrue(generator.next());
			assertEquals(c2
			assertFalse(generator.getSourceCommit().has(RevFlag.UNINTERESTING));
			assertEquals(1
			assertEquals(0
			assertEquals(1
			assertEquals(0
			assertEquals(1
			assertEquals("file.txt"

			assertTrue(generator.next());
			assertEquals(c1
			assertTrue(generator.getSourceCommit().has(RevFlag.UNINTERESTING));
			assertEquals(2
			assertEquals(1
			assertEquals(3
			assertEquals(0
			assertEquals(2
			assertEquals("file.txt"

			assertFalse(generator.next());
		} finally {
			generator.release();
		}
	}

	@Test
	public void testLinesAllDeletedShortenedWalk() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		String[] content2 = new String[] { "" };

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c3 = git.commit().setMessage("create file").call();

		BlameGenerator generator = new BlameGenerator(db
		try {
			assertEquals(3

			assertTrue(generator.next());
			assertEquals(c3
			assertFalse(generator.getSourceCommit().has(RevFlag.UNINTERESTING));
			assertEquals(0
			assertEquals(3

			assertFalse(generator.next());
		} finally {
			generator.release();
		}
	}

	private static String join(String... lines) {
		StringBuilder joined = new StringBuilder();
		for (String line : lines)
			joined.append(line).append('\n');
		return joined.toString();
	}
}
