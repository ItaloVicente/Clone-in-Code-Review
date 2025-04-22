package org.eclipse.jgit.attributes;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Before;
import org.junit.Test;

public class AttributesNodeDirCacheIteratorTest extends RepositoryTestCase {

	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private static Attribute EOL_LF = new Attribute("eol"

	private static Attribute DELTA_UNSET = new Attribute("delta"

	private Git git;

	private TreeWalk walk;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

	}

	@Test
	public void testRules() throws Exception {
		writeAttributesFile(".git/info/attributes"

		writeAttributesFile(".gitattributes"
		writeTrashFile("windows.file"
		writeTrashFile("windows.txt"
		writeTrashFile("readme.txt"

		writeAttributesFile("src/config/.gitattributes"
		writeTrashFile("src/config/readme.txt"
		writeTrashFile("src/config/windows.file"
		writeTrashFile("src/config/windows.txt"

		git.add().addFilepattern(".").call();

		walk = beginWalk();

		assertIteration(F
		assertIteration(F

		assertIteration(D

		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		assertIteration(F

		assertIteration(F
		assertIteration(F

		endWalk();
	}

	@Test
	public void testNoAttributes() throws Exception {
		writeTrashFile("l0.txt"
		writeTrashFile("level1/l1.txt"
		writeTrashFile("level1/level2/l2.txt"

		git.add().addFilepattern(".").call();
		walk = beginWalk();

		assertIteration(F

		assertIteration(D
		assertIteration(F

		assertIteration(D
		assertIteration(F

		endWalk();
	}

	@Test
	public void testEmptyGitAttributeFile() throws Exception {
		writeAttributesFile(".git/info/attributes"
		writeTrashFile("l0.txt"
		writeAttributesFile(".gitattributes"
		writeTrashFile("level1/l1.txt"
		writeTrashFile("level1/level2/l2.txt"

		git.add().addFilepattern(".").call();
		walk = beginWalk();

		assertIteration(F
		assertIteration(F

		assertIteration(D
		assertIteration(F

		assertIteration(D
		assertIteration(F

		endWalk();
	}

	@Test
	public void testNoMatchingAttributes() throws Exception {
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("levelA/.gitattributes"
		writeAttributesFile("levelB/.gitattributes"

		writeTrashFile("levelA/lA.txt"

		git.add().addFilepattern(".").call();
		walk = beginWalk();

		assertIteration(F

		assertIteration(D
		assertIteration(F
		assertIteration(F

		assertIteration(D
		assertIteration(F

		endWalk();
	}

	@Test
	public void testIncorrectAttributeFileName() throws Exception {
		writeAttributesFile("levelA/file.gitattributes"
		writeAttributesFile("gitattributes"

		writeTrashFile("l0.txt"
		writeTrashFile("levelA/lA.txt"

		git.add().addFilepattern(".").call();
		walk = beginWalk();

		assertIteration(F

		assertIteration(F

		assertIteration(D
		assertIteration(F
		assertIteration(F

		endWalk();
	}

	private void assertIteration(FileMode type
			throws IOException {
		assertIteration(type
	}

	private void assertIteration(FileMode type
			List<Attribute> nodeAttrs) throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type
		DirCacheIterator itr = walk.getTree(0
		assertNotNull("has tree"

		AttributesNode attributeNode = itr.getEntryAttributesNode(db
				.newObjectReader());
		assertAttributeNode(pathName

		if (D.equals(type))
			walk.enterSubtree();

	}

	private void assertAttributeNode(String pathName
			AttributesNode attributeNode
		if (attributeNode == null)
			assertTrue(nodeAttrs == null || nodeAttrs.isEmpty());
		else {

			Map<String
			attributeNode.getAttributes(pathName

			if (nodeAttrs != null && !nodeAttrs.isEmpty()) {
				for (Attribute attribute : nodeAttrs) {
					assertThat(entryAttributes.values()
				}
			} else {
				assertTrue(
						"The entry "
								+ pathName
								+ " should not have any attributes. Instead
								+ entryAttributes.toString()
						entryAttributes.isEmpty());
			}
		}
	}

	private void writeAttributesFile(String name
			throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules)
			data.append(line + "\n");
		writeTrashFile(name
	}

	private TreeWalk beginWalk() throws Exception {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new DirCacheIterator(db.readDirCache()));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}
}
