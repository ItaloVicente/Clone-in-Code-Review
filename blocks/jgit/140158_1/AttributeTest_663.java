package org.eclipse.jgit.api.blame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class BlameGeneratorTest extends RepositoryTestCase {
	@Test
	public void testBoundLineDelete() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content2 = new String[] { "third"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("create file").call();

			try (BlameGenerator generator = new BlameGenerator(db
				generator.push(null
				assertEquals(3

				assertTrue(generator.next());
				assertEquals(c2
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals("file.txt"

				assertTrue(generator.next());
				assertEquals(c1
				assertEquals(2
				assertEquals(1
				assertEquals(3
				assertEquals(0
				assertEquals(2
				assertEquals("file.txt"

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testRenamedBoundLineDelete() throws Exception {
		try (Git git = new Git(db)) {
			final String FILENAME_1 = "subdir/file1.txt";
			final String FILENAME_2 = "subdir/file2.txt";

			String[] content1 = new String[] { "first"
			writeTrashFile(FILENAME_1
			git.add().addFilepattern(FILENAME_1).call();
			RevCommit c1 = git.commit().setMessage("create file1").call();

			writeTrashFile(FILENAME_2
			git.add().addFilepattern(FILENAME_2).call();
			deleteTrashFile(FILENAME_1);
			git.rm().addFilepattern(FILENAME_1).call();
			git.commit().setMessage("rename file1.txt to file2.txt").call();

			String[] content2 = new String[] { "third"
			writeTrashFile(FILENAME_2
			git.add().addFilepattern(FILENAME_2).call();
			RevCommit c2 = git.commit().setMessage("change file2").call();

			try (BlameGenerator generator = new BlameGenerator(db
				generator.push(null
				assertEquals(3

				assertTrue(generator.next());
				assertEquals(c2
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals(FILENAME_2

				assertTrue(generator.next());
				assertEquals(c1
				assertEquals(2
				assertEquals(1
				assertEquals(3
				assertEquals(0
				assertEquals(2
				assertEquals(FILENAME_1

				assertFalse(generator.next());
			}

			try (BlameGenerator generator = new BlameGenerator(db
				generator.push(null
				BlameResult result = generator.computeBlameResult();

				assertEquals(3

				assertEquals(c2
				assertEquals(FILENAME_2

				assertEquals(c1
				assertEquals(FILENAME_1

				assertEquals(c1
				assertEquals(FILENAME_1
			}
		}
	}

	@Test
	public void testLinesAllDeletedShortenedWalk() throws Exception {
		try (Git git = new Git(db)) {
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

			try (BlameGenerator generator = new BlameGenerator(db
				generator.push(null
				assertEquals(3

				assertTrue(generator.next());
				assertEquals(c3
				assertEquals(0
				assertEquals(3

				assertFalse(generator.next());
			}
		}
	}

	private static String join(String... lines) {
		StringBuilder joined = new StringBuilder();
		for (String line : lines)
			joined.append(line).append('\n');
		return joined.toString();
	}
}
