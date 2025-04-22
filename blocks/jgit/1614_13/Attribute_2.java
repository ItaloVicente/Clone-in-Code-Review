package org.eclipse.jgit.attributes;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.attributes.Attribute.State;
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

	private static Attribute EOL_CRLF = new Attribute("eol"

	private static Attribute EOL_LF = new Attribute("eol"

	private static Attribute TEXT_SET = new Attribute("text"

	private static Attribute TEXT_UNSET = new Attribute("text"

	private static Attribute DELTA_UNSET = new Attribute("delta"

	private static Attribute CUSTOM_INFO = new Attribute("custom"

	private static Attribute CUSTOM_ROOT = new Attribute("custom"

	private static Attribute CUSTOM_PARENT = new Attribute("custom"

	private static Attribute CUSTOM_CURRENT = new Attribute("custom"

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
		assertEntry(F

		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(F
		assertEntry(F
	}

	@Test
	public void testNoAttributes() throws IOException {
		writeTrashFile("l0.txt"
		writeTrashFile("level1/l1.txt"
		writeTrashFile("level1/level2/l2.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(F
	}

	@Test
	public void testEmptyGitAttributeFile() throws IOException {
		writeAttributesFile(".git/info/attributes"
		writeTrashFile("l0.txt"
		writeAttributesFile(".gitattributes"
		writeTrashFile("level1/l1.txt"
		writeTrashFile("level1/level2/l2.txt"

		beginWalk();

		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(F
	}

	@Test
	public void testNoMatchingAttributes() throws IOException {
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("levelA/.gitattributes"
		writeAttributesFile("levelB/.gitattributes"

		writeTrashFile("levelA/lA.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F
	}

	@Test
	public void testPrecedenceInfo() throws IOException {
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"
		writeAttributesFile("level1/level2/.gitattributes"
				"*.txt custom=current");

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F
	}

	@Test
	public void testPrecedenceCurrent() throws IOException {
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"
		writeAttributesFile("level1/level2/.gitattributes"
				"*.txt custom=current");

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F
	}

	@Test
	public void testPrecedenceParent() throws IOException {
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(F
	}

	@Test
	public void testPrecedenceRoot() throws IOException {
		writeAttributesFile(".gitattributes"

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D

		assertEntry(D
		assertEntry(F
	}

	@Test
	public void testHierarchy() throws IOException {
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"
		writeAttributesFile("level1/level2/.gitattributes"

		writeTrashFile("l0.global"
		writeTrashFile("l0.local"

		writeTrashFile("level1/l1.global"
		writeTrashFile("level1/l1.local"

		writeTrashFile("level1/level2/l2.global"
		writeTrashFile("level1/level2/l2.local"

		beginWalk();

		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F

	}

	@Test
	public void testAggregation() throws IOException {
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"
		writeAttributesFile("level1/level2/.gitattributes"

		writeTrashFile("l0.txt"

		writeTrashFile("level1/l1.txt"

		writeTrashFile("level1/level2/l2.txt"

		beginWalk();

		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(F
				DELTA_UNSET);

	}

	@Test
	public void testOverriding() throws IOException {
		writeAttributesFile(".git/info/attributes"
				"*.txt custom=current"
				"*.txt custom=parent"
				"*.txt custom=root"
				"*.txt custom=info"
				"*.txt delta"
				"*.txt -delta"
				"*.txt eol=lf"
				"*.txt eol=crlf"
				"*.txt text"
				"*.txt -text");

		writeTrashFile("l0.txt"
		beginWalk();

		assertEntry(F
	}

	@Test
	public void testOverriding2() throws IOException {
		writeAttributesFile(".git/info/attributes"
				"*.txt custom=current custom=parent custom=root custom=info"
				"*.txt delta -delta"
				"*.txt eol=lf eol=crlf"
				"*.txt text -text");
		writeTrashFile("l0.txt"
		beginWalk();

		assertEntry(F
	}

	private void beginWalk() throws CorruptObjectException {
		walk = new TreeWalk(db);
		walk.addTree(new FileTreeIterator(db));
	}

	private void assertEntry(FileMode type
			Attribute... attributes) throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		WorkingTreeIterator itr = walk.getTree(0
		assertNotNull("has tree"
		List<Attribute> entryAttributes = itr.getEntryAttributes();
		if (attributes != null && attributes.length > 0) {
			for (Attribute attribute : attributes) {
				assertThat(entryAttributes
			}
		} else {
			assertTrue(
					"The entry "
							+ pathName
							+ " should not have any attributes. Instead
							+ entryAttributes.toString()
					entryAttributes.isEmpty());
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
