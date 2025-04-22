package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class AttributesHandlerTest extends RepositoryTestCase {
	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private TreeWalk walk;

	@Test
	public void testExpandNonMacro1() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testExpandNonMacro2() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testExpandNonMacro3() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testExpandNonMacro4() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testExpandBuiltInMacro1() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testExpandBuiltInMacro2() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testExpandBuiltInMacro3() throws Exception {
		setupRepo(null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testCustomGlobalMacro1() throws Exception {
		setupRepo(
				"[attr]foo a -b !c d=e"

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testCustomGlobalMacro2() throws Exception {
		setupRepo("[attr]foo a -b !c d=e"

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testCustomGlobalMacro3() throws Exception {
		setupRepo("[attr]foo a -b !c d=e"

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testCustomGlobalMacro4() throws Exception {
		setupRepo("[attr]foo a -b !c d=e"

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testInfoOverridesGlobal() throws Exception {
		setupRepo("[attr]foo bar1"
				"[attr]foo bar2"

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testWorkDirRootOverridesGlobal() throws Exception {
		setupRepo("[attr]foo bar1"
				null
				"[attr]foo bar3"

		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testInfoOverridesWorkDirRoot() throws Exception {
		setupRepo("[attr]foo bar1"
				"[attr]foo bar2"

		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testRecursiveMacro() throws Exception {
		setupRepo(
				"[attr]foo x bar -foo"
				null

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testCyclicMacros() throws Exception {
		setupRepo(
				"[attr]foo x -bar\n[attr]bar y -foo"

		walk = beginWalk();
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testRelativePaths() throws Exception {
		setupRepo("sub/ global"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
		writeTrashFile("sub/sub/b.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
				attrs("init foo subsub top top_sub"));
		endWalk();
	}

	@Test
	public void testNestedMatchNot() throws Exception {
		setupRepo(null
		writeTrashFile("foo.xml/bar.jar"
		writeTrashFile("foo.xml/bar.xml"
		writeTrashFile("sub/b.jar"
		writeTrashFile("sub/b.xml"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testNestedMatch() throws Exception {
		setupRepo(null
		writeTrashFile("foo/bar.jar"
		writeTrashFile("foo/bar.xml"
		writeTrashFile("sub/b.jar"
		writeTrashFile("sub/b.xml"
		writeTrashFile("sub/foo/b.jar"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testNestedMatchRecursive() throws Exception {
		setupRepo(null
		writeTrashFile("foo/bar.jar"
		writeTrashFile("foo/bar.xml"
		writeTrashFile("sub/b.jar"
		writeTrashFile("sub/b.xml"
		writeTrashFile("sub/foo/b.jar"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testStarMatchOnSlashNot() throws Exception {
		setupRepo(null
		writeTrashFile("sub/a.txt"
		writeTrashFile("foo/sext"
		writeTrashFile("foo/s.txt"
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
	public void testPrefixMatchNot() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testComplexPathMatch() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("sub/ndw"
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
	public void testStarPathMatch() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("sub/new/lower/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testDirectoryMatchSubSimple() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		writeTrashFile("sub/sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testDirectoryMatchSubRecursive() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		writeTrashFile("sub/sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack2() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		writeTrashFile("sub/sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testDirectoryMatchSubComplex() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testDirectoryMatch() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		writeTrashFile("foo/new"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	private static Collection<Attribute> attrs(String s) {
		return new AttributesRule("*"
	}

	private void assertIteration(FileMode type
			throws IOException {
		assertIteration(type
	}

	private void assertIteration(FileMode type
			Collection<Attribute> expectedAttrs) throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		if (expectedAttrs != null) {
			assertEquals(new ArrayList<>(expectedAttrs)
					new ArrayList<>(walk.getAttributes().getAll()));
		}

		if (D.equals(type))
			walk.enterSubtree();
	}

	private void setupRepo(
			String globalAttributesContent
			String infoAttributesContent
					throws Exception {
		FileBasedConfig config = db.getConfig();
		if (globalAttributesContent != null) {
			File f = new File(db.getDirectory()
			write(f
			config.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_ATTRIBUTESFILE
					f.getAbsolutePath());

		}
		if (infoAttributesContent != null) {
			File f = new File(db.getDirectory()
			write(f
		}
		config.save();

		if (rootAttributesContent != null) {
			writeAttributesFile(Constants.DOT_GIT_ATTRIBUTES
					rootAttributesContent);
		}

		if (subDirAttributesContent != null) {
			writeAttributesFile("sub/" + Constants.DOT_GIT_ATTRIBUTES
					subDirAttributesContent);
		}

		writeTrashFile("sub/a.txt"
	}

	private void writeAttributesFile(String name
			throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules)
			data.append(line + "\n");
		writeTrashFile(name
	}

	private TreeWalk beginWalk() {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}
}
