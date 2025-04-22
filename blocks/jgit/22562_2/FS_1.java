package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PathMatcherTest {
	protected File temporaryFolder;

	protected File[] testFiles;

	@Before
	public void setUp() throws IOException {
		temporaryFolder = createTempDirectory();

		testFiles = new File[8];
		testFiles[0] = createFile(temporaryFolder
		testFiles[1] = createFile(temporaryFolder
		testFiles[2] = createFile(temporaryFolder
				"test/long/winded/folder/abc");
		testFiles[3] = createFile(temporaryFolder
		testFiles[4] = createFile(temporaryFolder
		testFiles[5] = createFile(temporaryFolder
		testFiles[6] = createFile(temporaryFolder
		testFiles[7] = createFile(temporaryFolder
	}

	private static File createTempDirectory() throws IOException {
		File temporaryPath = File.createTempFile("JGitPathMatcherTest_"
				Long.toString(System.currentTimeMillis()));
		temporaryPath.delete();
		File tempFolder = new File(temporaryPath.getPath() + "_Folder");
		tempFolder.mkdir();
		return tempFolder;
	}

	@After
	public void tearDown() throws IOException {
		FileUtils.delete(temporaryFolder
	}

	@Test
	public void testMatchAcrossFolders() {
		final String globAll = "**";
		final String globAllEndInC = "**c";

		final PathMatcher matcherAll = getPathMatcher(globAll);
		final PathMatcher matcherAllEndInC = getPathMatcher(globAllEndInC);
		final PathMatcher matcherAllEndInCUnderFolderTest = getPathMatcher(globAllEndInCUnderFolderTest);

		final Set<File> all = getAllMatchesIn(temporaryFolder

		assertEquals(8

		final Set<File> endingInC = getAllMatchesIn(temporaryFolder
				matcherAllEndInC);

		assertEquals(4
		assertTrue(endingInC.contains(testFiles[0]));
		assertTrue(endingInC.contains(testFiles[1]));
		assertTrue(endingInC.contains(testFiles[2]));
		assertTrue(endingInC.contains(testFiles[3]));

		final Set<File> endingInCUnderFolderTest = getAllMatchesIn(
				temporaryFolder
		assertEquals(1
		assertTrue(endingInCUnderFolderTest.contains(testFiles[2]));
	}

	@Test
	public void testMatchStarWildcard() {
		final String globEndInC = "*c";
		final String globContainsA = "*a*";

		final PathMatcher matcherEndInC = getPathMatcher(globEndInC);
		final PathMatcher matcherContainsA = getPathMatcher(globContainsA);

		final Set<File> endInC = getAllMatchesIn(temporaryFolder

		assertEquals(1
		assertTrue(endInC.contains(testFiles[0]));

		final Set<File> containsA = getAllMatchesIn(temporaryFolder
				matcherContainsA);

		assertEquals(2
		assertTrue(containsA.contains(testFiles[0]));
		assertTrue(containsA.contains(testFiles[5]));
	}

	@Test
	public void testMatchUnaryWildcard() {
		final String glob1 = "a?c";
		final String glob2 = "de??";

		final PathMatcher matcher1 = getPathMatcher(glob1);
		final PathMatcher matcher2 = getPathMatcher(glob2);

		final Set<File> matched1 = getAllMatchesIn(temporaryFolder

		assertEquals(1
		assertTrue(matched1.contains(testFiles[0]));

		final Set<File> matched2 = getAllMatchesIn(temporaryFolder

		assertEquals(1
		assertTrue(matched2.contains(testFiles[5]));
	}

	@Test
	public void testMatchMixed() {

		final PathMatcher matcher1 = getPathMatcher(glob1);
		final PathMatcher matcher2 = getPathMatcher(glob2);

		final Set<File> matched1 = getAllMatchesIn(temporaryFolder

		assertEquals(4
		assertTrue(matched1.contains(testFiles[1]));
		assertTrue(matched1.contains(testFiles[2]));
		assertTrue(matched1.contains(testFiles[3]));
		assertTrue(matched1.contains(testFiles[4]));

		final Set<File> matched2 = getAllMatchesIn(temporaryFolder

		assertEquals(4
		assertTrue(matched2.contains(testFiles[1]));
		assertTrue(matched2.contains(testFiles[2]));
		assertTrue(matched2.contains(testFiles[6]));
		assertTrue(matched2.contains(testFiles[7]));
	}

	protected PathMatcher getPathMatcher(String glob) {
		return new PathMatcher_Java5(glob);
	}

	protected static Set<File> getAllMatchesIn(File matchRoot
			PathMatcher matcher) {
		final Set<File> matches = new LinkedHashSet<File>();
		for (File child : matchRoot.listFiles())
			matches.addAll(recursiveMatch(matchRoot
		return matches;
	}

	private static Set<File> recursiveMatch(File matchRoot
			PathMatcher matcher) {
		final Set<File> matches = new LinkedHashSet<File>();
		if (file.isDirectory()) {
			for (File child : file.listFiles())
				matches.addAll(recursiveMatch(matchRoot
		} else if (matcher.matches(FileUtils.relativize(matchRoot.getPath()
				file.getPath())))
			matches.add(file);
		return matches;
	}

	private static File createFile(File folder
		final File file = new File(folder
		final File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IOException("Couldn't create dir: " + parent);
		}
		file.createNewFile();
		return file;
	}

}
