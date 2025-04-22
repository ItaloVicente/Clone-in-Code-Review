package org.eclipse.jgit.attributes;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.junit.Test;

public class AttributesNodeWorkingTreeIteratorTest extends RepositoryTestCase {

	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private static Attribute EOL_CRLF = new Attribute("eol"

	private static Attribute EOL_LF = new Attribute("eol"

	private static Attribute DELTA_UNSET = new Attribute("delta"

	private static Attribute CUSTOM_VALUE = new Attribute("custom"

	private TreeWalk walk;

	@Test
	public void testRules() throws Exception {

		File customAttributeFile = File.createTempFile("tmp_"
				"customAttributeFile"
		customAttributeFile.deleteOnExit();

		JGitTestUtil.write(customAttributeFile
		db.getConfig().setString("core"
				customAttributeFile.getAbsolutePath());
		writeAttributesFile(".git/info/attributes"

		writeAttributesFile(".gitattributes"
		writeTrashFile("windows.file"
		writeTrashFile("windows.txt"
		writeTrashFile("global.txt"
		writeTrashFile("readme.txt"

		writeAttributesFile("src/config/.gitattributes"
		writeTrashFile("src/config/readme.txt"
		writeTrashFile("src/config/windows.file"
		writeTrashFile("src/config/windows.txt"

		walk = beginWalk();

		assertIteration(F
		assertIteration(F
				asList(CUSTOM_VALUE));
		assertIteration(F
				asList(CUSTOM_VALUE));

		assertIteration(D

		assertIteration(D
		assertIteration(F
		assertIteration(F
				asList(CUSTOM_VALUE));
		assertIteration(F
				null);
		assertIteration(F
				asList(EOL_CRLF)

		assertIteration(F
		assertIteration(F
				asList(CUSTOM_VALUE));

		endWalk();
	}


	@Test
	public void testNoAttributes() throws Exception {
		writeTrashFile("l0.txt"
		writeTrashFile("level1/l1.txt"
		writeTrashFile("level1/level2/l2.txt"

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

		walk = beginWalk();

		assertIteration(F

		assertIteration(D
		assertIteration(F
		assertIteration(F

		assertIteration(D
		assertIteration(F

		endWalk();
	}

	private void assertIteration(FileMode type
			throws IOException {
		assertIteration(type
				Collections.<Attribute> emptyList()
				Collections.<Attribute> emptyList());
	}

	private void assertIteration(FileMode type
			List<Attribute> nodeAttrs
			List<Attribute> globalAttrs)
			throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type
		WorkingTreeIterator itr = walk.getTree(0
		assertNotNull("has tree"

		AttributesNode attributeNode = itr.getEntryAttributesNode();
		assertAttributeNode(pathName
		AttributesNode infoAttributeNode = itr.getInfoAttributesNode();
		assertAttributeNode(pathName
		AttributesNode globalAttributeNode = itr.getGlobalAttributesNode();
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

	private TreeWalk beginWalk() throws CorruptObjectException {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}
}
