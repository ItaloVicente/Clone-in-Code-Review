package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.junit.Test;

public class AttributesNodeTest extends RepositoryTestCase {

	private static final FileMode D = FileMode.TREE;
	private static final FileMode F = FileMode.REGULAR_FILE;

	private TreeWalk walk;

	@Test
	public void testRules() throws IOException {
		writeAttributesFile(".git/info/attributes"

		writeAttributesFile(".gitattributes"
		writeTrashFile("windows.file"
		writeTrashFile("windows.txt"
		writeTrashFile("readme.txt"

		writeAttributesFile("src/config/.gitattributes"
		writeTrashFile("src/config/readme.txt"
		writeTrashFile("src/config/windows.file"
		writeTrashFile("src/config/windows.txt"

		beginWalk();
		assertEntry(F

		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(F
		assertEntry(F
		assertEntry(F
	}

	private void beginWalk() throws CorruptObjectException {
		walk = new TreeWalk(db);
		walk.addTree(new FileTreeIterator(db));
	}

	private void assertEntry(FileMode type
			String... attributes) throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		WorkingTreeIterator itr = walk.getTree(0
		assertNotNull("has tree"
		if (attributes != null) {
		}
		if (D.equals(type))
			walk.enterSubtree();
	}

	private void writeAttributesFile(String name
			throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules)
			data.append(line + "\n");
		writeTrashFile(name
	}
}
