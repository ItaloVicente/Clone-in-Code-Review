package org.eclipse.jgit.ignore;

import java.io.IOException;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class IgnoreNodeTest extends RepositoryTestCase {
	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private static final boolean ignored = true;

	private static final boolean tracked = false;

	private TreeWalk walk;

	public void testRules() throws IOException {
		writeIgnoreFile(".git/info/exclude"

		writeIgnoreFile(".gitignore"
		writeTrashFile("config/secret"
		writeTrashFile("mylib.c"
		writeTrashFile("mylib.c~"
		writeTrashFile("mylib.o"

		writeIgnoreFile("src/config/.gitignore"
		writeTrashFile("src/config/lex.out"
		writeTrashFile("src/config/config.c"
		writeTrashFile("src/config/config.c~"
		writeTrashFile("src/config/old/lex.out"

		walk = new TreeWalk(db);
		walk.reset();
		walk.addTree(new FileTreeIterator(db));

		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
	}

	private void assertEntry(FileMode type
			String pathName) throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		WorkingTreeIterator itr = walk.getTree(0
		assertNotNull("has tree"
		assertEquals("is ignored"
		if (D.equals(type))
			walk.enterSubtree();
	}

	private void writeIgnoreFile(String name
			throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules)
			data.append(line + "\n");
		writeTrashFile(name
	}
}
