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
		initCache();
	}

	public void testInitialization() {
		File test = new File(repo.getDirectory() + "/new/a/b1/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()

		boolean result = isIgnored(getRelativePath(test));
		assertFalse("Unexpected match for " + test.toString()

		File folder = test.getParentFile();
		IgnoreNode rules = null;
		String fp = folder.getAbsolutePath();
		while (!folder.equals(repo.getDirectory()) && fp.length() > 0) {
			rules = cache.getRules(getRelativePath(folder));
			assertNotNull("Ignore file not initialized for " + fp
			if (getRelativePath(folder).endsWith("new/a"))
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

		test = new File("/tmp/not/part/of/repo/path");
	}

	public void testRules() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();
		initCache();

		File test = new File(repo.getDirectory()
		String path = test.getAbsolutePath();
		assertTrue("Could not find test file " + path

		IgnoreNode baseRules = cache.getRules("");
		assertNotNull("Could not find base rules"


		boolean result = isIgnored(getRelativePath(test));
		assertEquals(3
		assertTrue(repo.getDirectory().toURI().equals(baseRules.getBaseDir().toURI()));
		assertTrue("Did not match file " + test.toString()
		assertNotIgnored("notignored");
		assertNotIgnored("/src/test.stp");
		assertNotIgnored("not/mentioned/file.txt");


		test = new File(repo.getDirectory()
		assertNotIgnored("new/a/b2/d/test.stp");
		assertNotIgnored("new/a/b2/d/");
		assertNotIgnored("new/a/b2/d");

		test = new File(repo.getDirectory()
		assertIgnored("new/a/b1/c");
		assertIgnored("new/a/b1/c/anything.c");
		assertIgnored("new/a/b1/c/and.o");
		assertIgnored("new/a/b1/c/everything.d");
		assertIgnored("new/a/b1/c/everything.d");
		assertIgnored("new/a/b1/c/shouldbeignored.txt");

		assertNotIgnored("notarealfile");
		assertNotIgnored("/notarealfile");
		assertIgnored("new/notarealfile");
		assertIgnored("new/notarealfile/fake");
		assertIgnored("new/a/notarealfile");
		assertIgnored("new/a/b1/notarealfile");

		createIgnoreFile(repo.getDirectory() + "/new/a/b2/.gitignore"
		test = new File(repo.getDirectory()
		initCache();
		baseRules = cache.getRules("new/a/b2");
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
		assertNotIgnored("/anything");
		assertNotIgnored("/new/anything");
		assertNotIgnored("/src/anything");
	}


	public void testPriorities() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();
		initCache();

		File test = new File(repo.getDirectory()
		assertTrue("Resource file " + test.getName() + " is missing"

		IgnoreNode node = cache.getRules("src");
		assertNotNull("Excludes file was not initialized"

		assertIgnored("src/a.c");
		assertIgnored("test.stp");
		assertIgnored("src/blank.stp");
		assertNotIgnored("notignored");
		assertNotIgnored("src/test.stp");

		assertEquals(4

		assertIgnored("new/a/b2/c/notarealfile2");
		assertIgnored("new/notarealfile");
		assertIgnored("new/a/notarealfile");
		assertNotIgnored("new/a/b2/c/test.stp");
		assertNotIgnored("new/a/b2/c");
		assertNotIgnored("new/a/b2/nonexistent");

	}

	private void assertNotIgnored(String relativePath) {
		File test = new File(repo.getDirectory()
		assertFalse("Should not match " + test.toString()
	}

	private void assertIgnored(String relativePath) {
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

		createIgnoreFile(repo.getDirectory() + "/.git/info/exclude"
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

	private String getRelativePath(File file) {
		String retVal = repo.getDirectory().toURI().relativize(file.toURI()).getPath();
		if (retVal.length() == file.getAbsolutePath().length())
			fail("Not a child of the git directory");
		if (retVal.endsWith("/")) {
			retVal = retVal.substring(0
		}
		return retVal;
	}

	private void initCache() {
		try {
			cache.initialize();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Could not initialize cache");
		}
	}

}
