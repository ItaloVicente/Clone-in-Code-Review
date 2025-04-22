package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreeWalkAttributeTest extends RepositoryTestCase {

	private static final FileMode M = FileMode.MISSING;

	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private static Attribute EOL_CRLF = new Attribute("eol"

	private static Attribute EOL_LF = new Attribute("eol"

	private static Attribute TEXT_SET = new Attribute("text"

	private static Attribute TEXT_UNSET = new Attribute("text"

	private static Attribute DELTA_UNSET = new Attribute("delta"

	private static Attribute DELTA_SET = new Attribute("delta"

	private static Attribute CUSTOM_GLOBAL = new Attribute("custom"

	private static Attribute CUSTOM_INFO = new Attribute("custom"

	private static Attribute CUSTOM_ROOT = new Attribute("custom"

	private static Attribute CUSTOM_PARENT = new Attribute("custom"

	private static Attribute CUSTOM_CURRENT = new Attribute("custom"

	private static Attribute CUSTOM2_UNSET = new Attribute("custom2"
			State.UNSET);

	private static Attribute CUSTOM2_SET = new Attribute("custom2"

	private TreeWalk walk;

	private TreeWalk ci_walk;

	private Git git;

	private File customAttributeFile;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		if (customAttributeFile != null)
			customAttributeFile.delete();
	}

	@Test
	public void testCheckinCheckoutDifferences() throws IOException
			NoFilepatternException

		writeGlobalAttributeFile("globalAttributesFile"
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"
		writeAttributesFile("level1/level2/.gitattributes"

		writeTrashFile("l0.txt"

		writeTrashFile("level1/l1.txt"

		writeTrashFile("level1/level2/l2.txt"

		git.add().addFilepattern(".").call();

		beginWalk();

		writeGlobalAttributeFile("globalAttributesFile"
		writeAttributesFile(".git/info/attributes"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"
		writeAttributesFile("level1/level2/.gitattributes"

		assertEntry(F
		assertEntry(F
				asSet(EOL_LF

		assertEntry(D
		assertEntry(F
		assertEntry(F
				asSet(EOL_LF
				asSet(EOL_LF

		assertEntry(D
		assertEntry(F
		assertEntry(F
				asSet(EOL_LF
				asSet(EOL_LF

		endWalk();
	}

	@Test
	public void testIndexOnly() throws IOException
			GitAPIException {
		List<File> attrFiles = new ArrayList<File>();
		attrFiles.add(writeGlobalAttributeFile("globalAttributesFile"
				"*.txt -custom2"));
		attrFiles.add(writeAttributesFile(".git/info/attributes"
				"*.txt eol=crlf"));
		attrFiles
				.add(writeAttributesFile(".gitattributes"
		attrFiles
				.add(writeAttributesFile("level1/.gitattributes"
		attrFiles.add(writeAttributesFile("level1/level2/.gitattributes"
				"*.txt -delta"));

		writeTrashFile("l0.txt"

		writeTrashFile("level1/l1.txt"

		writeTrashFile("level1/level2/l2.txt"

		git.add().addFilepattern(".").call();

		for (File attrFile : attrFiles)
			attrFile.delete();

		beginWalk();

		assertEntry(M
		assertEntry(F

		assertEntry(D
		assertEntry(M
		assertEntry(F

		asSet(CUSTOM_ROOT

		assertEntry(D
		assertEntry(M
		assertEntry(F

		asSet(CUSTOM_ROOT

		endWalk();
	}

	@Test
	public void testIndexOnly2()
			throws IOException
		File l2 = writeTrashFile("level1/level2/l2.txt"
		writeTrashFile("level1/level2/l1.txt"

		git.add().addFilepattern(".").call();

		writeAttributesFile(".gitattributes"
		assertTrue(l2.delete());

		beginWalk();

		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(M

		endWalk();
	}

	@Test
	public void testRules() throws IOException
			GitAPIException {
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

		git.add().addFilepattern(".").call();

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

		endWalk();
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

		endWalk();
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

		endWalk();
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

		endWalk();
	}

	@Test
	public void testPrecedenceInfo() throws IOException {
		writeGlobalAttributeFile("globalAttributesFile"
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

		endWalk();
	}

	@Test
	public void testPrecedenceCurrent() throws IOException {
		writeGlobalAttributeFile("globalAttributesFile"
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

		endWalk();
	}

	@Test
	public void testPrecedenceParent() throws IOException {
		writeGlobalAttributeFile("globalAttributesFile"
		writeAttributesFile(".gitattributes"
		writeAttributesFile("level1/.gitattributes"

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(F

		endWalk();
	}

	@Test
	public void testPrecedenceRoot() throws IOException {
		writeGlobalAttributeFile("globalAttributesFile"
		writeAttributesFile(".gitattributes"

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(F

		assertEntry(D

		assertEntry(D
		assertEntry(F

		endWalk();
	}

	@Test
	public void testPrecedenceGlobal() throws IOException {
		writeGlobalAttributeFile("globalAttributesFile"

		writeTrashFile("level1/level2/file.txt"

		beginWalk();

		assertEntry(D

		assertEntry(D
		assertEntry(F

		endWalk();
	}

	@Test
	public void testHierarchyBothIterator() throws IOException
			NoFilepatternException
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

		git.add().addFilepattern(".").call();

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

		endWalk();

	}

	@Test
	public void testHierarchyWorktreeOnly()
			throws IOException
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

		endWalk();

	}

	@Test
	public void testAggregation() throws IOException {
		writeGlobalAttributeFile("globalAttributesFile"
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
				asSet(EOL_CRLF

		assertEntry(D
		assertEntry(F
		assertEntry(
				F
				"level1/level2/l2.txt"
				asSet(EOL_CRLF
						CUSTOM2_UNSET));

		endWalk();

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
				asSet(TEXT_UNSET

		endWalk();
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
				asSet(TEXT_UNSET

		endWalk();
	}

	@Test
	public void testRulesInherited() throws Exception {
		writeAttributesFile(".gitattributes"
		writeTrashFile("src/config/readme.txt"
		writeTrashFile("src/config/windows.file"

		beginWalk();

		assertEntry(F
		assertEntry(D
		assertEntry(D

		assertEntry(F
		assertEntry(F
				Collections.<Attribute> emptySet());

		endWalk();
	}

	private void beginWalk() throws NoWorkTreeException
		walk = new TreeWalk(db);
		walk.addTree(new FileTreeIterator(db));
		walk.addTree(new DirCacheIterator(db.readDirCache()));

		ci_walk = new TreeWalk(db);
		ci_walk.setOperationType(OperationType.CHECKIN_OP);
		ci_walk.addTree(new FileTreeIterator(db));
		ci_walk.addTree(new DirCacheIterator(db.readDirCache()));
	}

	private void assertEntry(FileMode type
			Set<Attribute> forBothOperaiton) throws IOException {
		assertEntry(type
	}

	private void assertEntry(FileMode type
		assertEntry(type
				Collections.<Attribute> emptySet());
	}

	private void assertEntry(FileMode type
			Set<Attribute> checkinAttributes
			throws IOException {
		assertTrue("walk has entry"
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		assertEquals(checkinAttributes
				asSet(ci_walk.getAttributes().getAll()));
		assertEquals(checkoutAttributes
				asSet(walk.getAttributes().getAll()));

		if (D.equals(type)) {
			walk.enterSubtree();
			ci_walk.enterSubtree();
		}
	}

	private static Set<Attribute> asSet(Collection<Attribute> attributes) {
		Set<Attribute> ret = new HashSet<Attribute>();
		for (Attribute a : attributes) {
			ret.add(a);
		}
		return (ret);
	}

	private File writeAttributesFile(String name
			throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules)
			data.append(line + "\n");
		return writeTrashFile(name
	}

	private File writeGlobalAttributeFile(String fileName
			throws IOException {
		customAttributeFile = File.createTempFile("tmp_"
		customAttributeFile.deleteOnExit();
		StringBuilder attributesFileContent = new StringBuilder();
		for (String attr : attributes) {
			attributesFileContent.append(attr).append("\n");
		}
		JGitTestUtil.write(customAttributeFile
				attributesFileContent.toString());
		db.getConfig().setString("core"
				customAttributeFile.getAbsolutePath());
		return customAttributeFile;
	}

	static Set<Attribute> asSet(Attribute... attrs) {
		HashSet<Attribute> result = new HashSet<Attribute>();
		for (Attribute attr : attrs)
			result.add(attr);
		return result;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
		assertFalse("Not all files tested"
	}
}
