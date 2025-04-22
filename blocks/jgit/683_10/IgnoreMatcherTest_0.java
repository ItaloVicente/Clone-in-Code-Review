package org.eclipse.jgit.ignore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.JGitTestUtil;

public class IgnoreCacheTest extends TestCase {

	private File ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
	private Repository repo;
	private SimpleIgnoreCache cache;
	private final ArrayList<File> toDelete = new ArrayList<File>();


	public void tearDown() {
		deleteIgnoreFiles();
		cache.clear();
		toDelete.clear();
	}

	public void setUp() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"

		try {
			repo = new Repository(ignoreTestDir);
		} catch (IOException e) {
			fail("IOException when creating repository at" + ignoreTestDir);
		}
		cache = new SimpleIgnoreCache(repo);
	}

	public void testInitialization() {
		File test = new File(repo.getDirectory() + "/new/a/b1/test.stp".replaceAll("/"
		assertTrue("Missing file " + test.getAbsolutePath()
		initCache(test);

		boolean result = isIgnored(getRelativePath(test));
		assertFalse("Unexpected match for " + test.toString()

		File folder = test.getParentFile();
		IgnoreNode rules = null;
		String fp = folder.getAbsolutePath();
		while (!folder.equals(repo.getDirectory()) && fp.length() > 0) {
			rules = cache.getRules(getRelativePath(fp));
			assertNotNull("Ignore file not initialized for " + fp
			if (fp.equals(repo.getDirectory() + "/new/a".replaceAll("/"
				assertEquals("Ignore file not initialized for " + fp
			else {
				assertEquals("Ignore file not initialized for " + fp
			}
			folder = folder.getParentFile();
			fp = folder.getAbsolutePath();
		}
		if (rules != null)
			assertEquals(1
		else
			fail("Base directory not initialized");

		test = new File("/tmp/not/part/of/repo/path".replaceAll("/"
		initCache(test);
	}

	public void testRules() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();

		File test = new File(repo.getDirectory()
		String path = test.getAbsolutePath();
		assertTrue("Could not find test file " + path
		initCache(test);

		IgnoreNode baseRules = cache.getRules("");
		assertNotNull("Could not find base rules"


		boolean result = isIgnored(getRelativePath(path));
		assertEquals(3
		assertEquals(repo.getDirectory().getAbsolutePath()
		assertTrue("Did not match file " + test.toString()
		assertNotMatched("notignored");
		assertNotMatched("/src/test.stp");
		assertNotMatched("not/mentioned/file.txt");


		test = new File(repo.getDirectory()
		initCache(test);
		assertNotMatched("new/a/b2/d/test.stp");
		assertNotMatched("new/a/b2/d/");
		assertNotMatched("new/a/b2/d");

		test = new File(repo.getDirectory()
		initCache(test);
		assertMatched("new/a/b1/c");
		assertMatched("new/a/b1/c/anything.c");
		assertMatched("new/a/b1/c/and.o");
		assertMatched("new/a/b1/c/everything.d");

		assertNotMatched("notarealfile");
		assertNotMatched("/notarealfile");
		assertMatched("new/notarealfile");
		assertMatched("new/notarealfile/fake");
		assertMatched("new/a/notarealfile");
		assertMatched("new/a/b1/notarealfile");

		createIgnoreFile(repo.getDirectory() + "/new/a/b2/.gitignore".replaceAll("/"
		test = new File(repo.getDirectory()
		initCache(test);
		baseRules = cache.getRules("/new/a/b2".replaceAll("/"
		assertNotNull(baseRules);
		baseRules.clear();
		assertEquals(baseRules.getRules().size()
		try {
			assertFalse("Node not properly cleared"
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO exception when testing base rules");
		}

		assertNotNull(cache.getRules(""));
		assertFalse(cache.isEmpty());
		cache.clear();
		assertNull(cache.getRules(""));
		assertTrue(cache.isEmpty());
		assertNotMatched("/anything");
		assertNotMatched("/new/anything");
		assertNotMatched("/src/anything");
	}

	public void testPriorities() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();

		File test = new File(repo.getDirectory()
		assertTrue("Resource file " + test.getName() + " is missing"
		initCache(test);

		IgnoreNode node = cache.getRules("/src");
		assertNotNull("Excludes file was not initialized"

		assertMatched("src/a.c");
		assertMatched("test.stp");
		assertMatched("src/blank.stp");
		assertNotMatched("notignored");
		assertNotMatched("src/test.stp");

		assertEquals(4

		initCache(new File(repo.getDirectory()
		assertMatched("new/a/b2/c/notarealfile2");
		assertMatched("new/notarealfile");
		assertMatched("new/a/notarealfile");
		assertNotMatched("new/a/b2/c/test.stp");
		assertNotMatched("new/a/b2/c");
		assertNotMatched("new/a/b2/nonexistent");

	}

	private void assertNotMatched(String relativePath) {
		File test = new File(repo.getDirectory()
		assertFalse("Should not match " + test.toString()
	}

	private void assertMatched(String relativePath) {
		File test = new File(repo.getDirectory()
		assertTrue("Failed to match " + test.toString()
	}

	private void createIgnoreFile(String path
		File ignoreFile = new File(path);
		ignoreFile.delete();

		try {
			if (!ignoreFile.createNewFile()) {
				fail("Could not create ignore file" + ignoreFile.getAbsolutePath());
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter (ignoreFile));
			for (String s : contents)
				bw.write(s + System.getProperty("line.separator"));
			bw.flush();
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("Could not create exclude file");
		}
	}

	private void createExcludeFile() {
		String[] content = new String[2];
		content[0] = "/test.stp";
		content[1] = "/notignored";

		File parent= new File(repo.getDirectory()
		if (!parent.exists())
			parent.mkdirs();

		createIgnoreFile(repo.getDirectory() + "/.git/info/exclude".replaceAll("/"
	}

	private void deleteIgnoreFiles() {
		for (File f : toDelete)
			f.delete();

		File f = new File(ignoreTestDir.getAbsoluteFile()
		f.delete();
		f = new File(ignoreTestDir.getAbsoluteFile()
		f.delete();
	}

	private boolean isIgnored(String path) {
		try {
			return cache.isIgnored(path);
		} catch (IOException e) {
			fail("IOException when attempting to check ignored status");
		}
		return false;
	}

	private String getRelativePath(String path) {
		String retVal = path.replaceFirst(repo.getDirectory().getAbsolutePath()
		if (retVal.length() == path.length())
			fail("Not a child of the git directory");
		return retVal;
	}

	private String getRelativePath(File file) {
		String retVal = file.getAbsolutePath().replaceFirst(repo.getDirectory().getAbsolutePath()
		if (retVal.length() == file.getAbsolutePath().length())
			fail("Not a child of the git directory");
		return retVal;
	}

	private void initCache(File test) {
		cache.partiallyInitialize(test);
	}

}
