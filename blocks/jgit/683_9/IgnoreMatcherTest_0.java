package org.eclipse.jgit.ignore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.eclipse.jgit.util.JGitTestUtil;

public class IgnoreCacheTest extends TestCase {

	private File ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
	private final ArrayList<File> toDelete = new ArrayList<File>();

	public void tearDown() {
		deleteIgnoreFiles();
	}

	public void setUp() {
		toDelete.clear();
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();
	}

	public void testInitialization() {
		SimpleIgnoreCache cache = new SimpleIgnoreCache(ignoreTestDir);
		File test = new File(ignoreTestDir.getAbsolutePath() + "/new/a/b1/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()

		try {
			cache.partiallyInitialize(test);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException when attempting to lazy initialize");
		}

		boolean result = cache.isIgnored(test);
		assertFalse("Unexpected match for " + test.toString()

		File folder = test.getParentFile();

		IgnoreNode rules = null;
		while (!folder.getAbsolutePath().equals(ignoreTestDir.getAbsolutePath()) && folder.getAbsolutePath().length() > 0) {
			rules = cache.getRules(folder.getAbsolutePath());
			assertNotNull("Ignore file was not initialized for " + folder.getAbsolutePath()
			assertEquals(1
			folder = folder.getParentFile();
		}
		if (rules != null)
			assertEquals(1
		else
			fail("Ignore nodes not initialized");
	}

	public void testRules() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();

		SimpleIgnoreCache cache = new SimpleIgnoreCache(ignoreTestDir);
		File test = new File(ignoreTestDir.getAbsolutePath() + "/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()

		try {
			cache.partiallyInitialize(test);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException when attempting to lazy initialize");
		}

		IgnoreNode baseRules = cache.getRules(ignoreTestDir.getAbsolutePath());
		assertNotNull("Excludes file was not initialized"

		boolean result = cache.isIgnored(test);
		assertEquals(3
		assertEquals(ignoreTestDir.getAbsolutePath()

		assertTrue("Did not match file " + test.toString()

		test = new File(ignoreTestDir.getAbsolutePath() + "/notignored");
		assertFalse("Abnormal priority for " + test.toString()

		test = new File(ignoreTestDir.getAbsolutePath() + "/src/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()
		assertFalse("Unexpected match for " + test.toString()

		test = new File("/not/a/valid/path/at/all.txt");
		assertFalse("Cache matched non-existent file"
		try {
			assertFalse("Node matched non-existent file"
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO exception when testing base rules");
		}

		test = new File(ignoreTestDir.getAbsolutePath() + "/new/a/b2/c/test.stp");
		try {
			cache.partiallyInitialize(test);
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("IOException when attempting to lazy initialize");
		}

		createIgnoreFile(ignoreTestDir.getAbsolutePath() + "/new/a/b2/.gitignore"
		try {
			cache.partiallyInitialize(test);
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("IOException when attempting to lazy initialize");
		}

		baseRules = cache.getRules(ignoreTestDir.getAbsolutePath() + "/new/a/b2");
		assertNotNull(baseRules);
		baseRules.clear();
		assertEquals(baseRules.getRules().size()
		try {
			assertFalse("Node not properly cleared"
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO exception when testing base rules");
		}


		assertNotNull(cache.getRules(ignoreTestDir.getAbsolutePath()));
		assertFalse(cache.isEmpty());
		cache.clear();
		assertNull(cache.getRules(ignoreTestDir.getAbsolutePath()));
		assertTrue(cache.isEmpty());
	}

	public void testPriorities() {
		ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		createExcludeFile();

		SimpleIgnoreCache cache = new SimpleIgnoreCache(ignoreTestDir);
		File test = new File(ignoreTestDir.getAbsolutePath() + "/src/test.stp");
		assertTrue("Resource file " + test.getName() + " is missing"
		try {
			cache.partiallyInitialize(test);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException when attempting to lazy initialize");
		}
		IgnoreNode gitignore = cache.getRules(ignoreTestDir.getAbsolutePath() + "/src");
		assertNotNull("Excludes file was not initialized"

		boolean result = cache.isIgnored(test);

		assertEquals(4
		assertFalse("Unexpected match for " + test.toString()

		test = new File(ignoreTestDir.getAbsolutePath() + "/src/a.c");
		assertTrue("Failed to match " + test.toString()

		test = new File(ignoreTestDir.getAbsolutePath() + "/new/a/b2/c/test.stp");
		assertFalse("Failed to match " + test.toString()
	}

	private void createIgnoreFile(String path
		assertTrue("Test resource directory is not a directory"
		File ignoreFile = new File(path);
		ignoreFile.delete();
		toDelete.add(ignoreFile);

		try {
			if (!ignoreFile.createNewFile()) {
				fail("Could not create ignore file" + ignoreFile.getAbsolutePath());
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter (ignoreFile));
			for (String s : contents)
				bw.write(s + "\n");
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

		File parent= new File(ignoreTestDir.getAbsolutePath() + "/.git/info");
		if (!parent.exists())
			parent.mkdirs();

		createIgnoreFile(ignoreTestDir.getAbsolutePath() + "/.git/info/exclude"
	}

	private void deleteIgnoreFiles() {
		for (File f : toDelete)
			f.delete();

		File f = new File(ignoreTestDir.getAbsoluteFile() + "/.git/info");
		f.delete();
		f = new File(ignoreTestDir.getAbsoluteFile() + "/.git");
		f.delete();
	}
}
