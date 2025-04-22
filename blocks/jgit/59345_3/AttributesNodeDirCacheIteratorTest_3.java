package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class AttributesHierarchyTest extends RepositoryTestCase {

	@Test
	public void testExpandNonMacro() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy(null

		assertListEquals(attrs("text")
		assertListEquals(attrs("-text")
		assertListEquals(attrs("!text")
		assertListEquals(attrs("text=auto")
				manager.expandMacro(attr("text=auto")));
	}

	@Test
	public void testExpandBuiltInMacro() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy(null

		assertListEquals(attrs("binary -diff -merge -text")
				manager.expandMacro(attr("binary")));
		assertListEquals(attrs("-binary diff merge text")
				manager.expandMacro(attr("-binary")));
		assertListEquals(attrs("!binary !diff !merge !text")
				manager.expandMacro(attr("!binary")));
	}

	@Test
	public void testCustomGlobalMacro() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy(
				"[attr]foo a -b !c d=e"

		assertListEquals(attrs("foo a -b !c d=e")
				manager.expandMacro(attr("foo")));
		assertListEquals(attrs("-foo -a b !c d=e")
				manager.expandMacro(attr("-foo")));
		assertListEquals(attrs("!foo !a !b !c !d")
				manager.expandMacro(attr("!foo")));
		assertListEquals(attrs("foo=bar a -b !c d=bar")
				manager.expandMacro(attr("foo=bar")));
	}

	@Test
	public void testInfoOverridesGlobal() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy("[attr]foo bar1"
				"[attr]foo bar2"

		assertListEquals(attrs("foo bar2")
	}

	@Test
	public void testWorkDirRootOverridesGlobal() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy("[attr]foo bar1"
				"[attr]foo bar3");

		assertListEquals(attrs("foo bar3")
	}

	@Test
	public void testInfoOverridesWorkDirRoot() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy("[attr]foo bar1"
				"[attr]foo bar2"

		assertListEquals(attrs("foo bar2")
	}

	@Test
	public void testRecursiveMacro() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy("[attr]foo x bar -foo"
				null

		assertListEquals(attrs("-foo -x -bar")
				manager.expandMacro(attr("foo")));
	}

	@Test
	public void testCyclicMacros() throws Exception {
		AttributesHierarchy manager = setupAttributesHierarchy(
				"[attr]foo x -bar\n[attr]bar y -foo"

		assertListEquals(attrs("foo x -bar -y")
				manager.expandMacro(attr("foo")));
	}

	private static Collection<Attribute> attrs(String s) {
		return new AttributesRule("*"
	}

	private static Attribute attr(String s) {
		return new AttributesRule("*"
	}

	private static <T> void assertListEquals(Collection<T> expected
			Collection<T> actual) {
		assertEquals(new ArrayList<T>(expected)
	}

	private AttributesHierarchy setupAttributesHierarchy(
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

		if (workDirRootAttributesContent != null) {
			createFile(Constants.DOT_GIT_ATTRIBUTES
					workDirRootAttributesContent);
		}
		return db.getAttributesHierarchy();
	}

	private void createFile(String path
			throws Exception {
		int pos = path.lastIndexOf('/');
		if (pos < 0) {
			writeTrashFile(path
		} else {
			writeTrashFile(path.substring(0
					content);
		}
	}
}
