package org.eclipse.jgit.api.blame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.Line;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class BlameGeneratorTest extends RepositoryTestCase {

	private String join(String... lines) {
		StringBuilder joined = new StringBuilder();
		for (String line : lines)
			joined.append(line).append('\n');
		return joined.toString();
	}

	@Test
	public void testBoundLineDelete() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		String[] content2 = new String[] { "first"

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

		BlameGenerator generator = new BlameGenerator(db
		try {
			List<Line> lines = generator.start();
			assertEquals(3
			for (Line line : lines)
				assertFalse(line.isBound());

			assertTrue(generator.next());

			for (Line line : lines)
				if (line.getNumber() == 2)
					assertTrue(line.isBound());
				else
					assertFalse(line.isBound());

			assertFalse(generator.next());

			for (Line line : lines)
				assertTrue(line.isBound());
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
		git.commit().setMessage("create file").call();

		BlameGenerator generator = new BlameGenerator(db
		try {
			List<Line> lines = generator.start();
			assertEquals(3
			for (Line line : lines)
				assertFalse(line.isBound());

			assertFalse(generator.next());

			for (Line line : lines)
				assertTrue(line.isBound());
		} finally {
			generator.release();
		}
	}

}
