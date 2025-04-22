package org.eclipse.jgit.ignore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.jgit.util.JGitTestUtil;

public class IgnoreCacheTest extends TestCase {


	public void testInitialization() {
		File ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		SimpleIgnoreCache cache = new SimpleIgnoreCache(ignoreTestDir);
		File test = new File(ignoreTestDir.getAbsolutePath() + "/new/a/b1/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()

		try {
			cache.partiallyInitialize(test);
		} catch (IOException e) {
			fail("IOException when attempting to lazy initialize");
			e.printStackTrace();
		}

		boolean result = cache.isIgnored(test);
		assertFalse("Unexpected match for " + test.toString()

		File folder = test;

		while (!folder.getAbsolutePath().equals(ignoreTestDir.getAbsolutePath()) && folder.length() > 0) {
			folder = folder.getParentFile();
			IgnoreNode rules = cache.getRules(folder.getAbsolutePath());
			assertNotNull("Ignore file was not initialized for " + folder.getAbsolutePath()
			assertEquals(1
		}
	}

	public void testExclude() {
		File ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is not a directory"
		File excludeFile = new File(ignoreTestDir.getAbsolutePath() + "/.git/info/exclude");

		if (!excludeFile.exists()) {

			File exclude = new File(ignoreTestDir.getAbsolutePath() + "/.git/info");
			if (!exclude.exists() && !exclude.mkdirs()) {
				fail("Could not create .git/info directory");
			}

			try {
				if (!excludeFile.createNewFile()) {
					fail("Could not create exclude file");
				}

				BufferedWriter bw = new BufferedWriter(new FileWriter (excludeFile));
				bw.write("/test.stp\n");
				bw.write("/notignored");
				bw.flush();
				bw.close();

			} catch (IOException e1) {
				fail("Could not create exclude file");
				e1.printStackTrace();
			}
		}

		SimpleIgnoreCache cache = new SimpleIgnoreCache(ignoreTestDir);
		File test = new File(ignoreTestDir.getAbsolutePath() + "/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()

		try {
			cache.partiallyInitialize(test);
		} catch (IOException e) {
			fail("IOException when attempting to lazy initialize");
			e.printStackTrace();
		}

		IgnoreNode baseRules = cache.getRules(ignoreTestDir.getAbsolutePath());
		assertNotNull("Excludes file was not initialized"

		boolean result = cache.isIgnored(test);
		assertEquals(3

		assertTrue("Did not match file " + test.toString()

		test = new File(ignoreTestDir.getAbsolutePath() + "/notignored");
		assertFalse("Abnormal priority for " + test.toString()


		test = new File(ignoreTestDir.getAbsolutePath() + "/src/test.stp");
		assertTrue("Missing file " + test.getAbsolutePath()
		assertFalse("Unexpected match for " + test.toString()
	}

	public void testPriorities() {
		File ignoreTestDir = JGitTestUtil.getTestResourceFile("excludeTest");
		assertTrue("Test resource directory is missing"
		assertTrue("Test resource directory is not a directory"
		SimpleIgnoreCache cache = new SimpleIgnoreCache(ignoreTestDir);
		File test = new File(ignoreTestDir.getAbsolutePath() + "/src/test.stp");
		assertTrue("Resource file " + test.getName() + " is missing"
		try {
			cache.partiallyInitialize(test);
		} catch (IOException e) {
			fail("IOException when attempting to lazy initialize");
			e.printStackTrace();
		}
		IgnoreNode gitignore = cache.getRules(ignoreTestDir.getAbsolutePath() + "/src");
		assertNotNull("Excludes file was not initialized"


		boolean result = cache.isIgnored(test);

		assertEquals(4
		assertFalse("Unexpected match for " + test.toString()

		test = new File(ignoreTestDir.getAbsolutePath() + "/src/a.c");
		result = cache.isIgnored(test);
		assertTrue("Failed to match " + test.toString()
	}



}

