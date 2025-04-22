package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class MacroExpanderTest extends RepositoryTestCase {
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

	private TreeWalk beginWalk() throws CorruptObjectException {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}
}
