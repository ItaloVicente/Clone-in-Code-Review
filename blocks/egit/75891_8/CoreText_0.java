package org.eclipse.egit.core.internal.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.util.SystemReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class RepositoryPathCheckerTest {

	private RepositoryPathChecker checker;

	@Before
	public void setup() {
		checker = new RepositoryPathChecker();
	}

	@Test
	public void testLocalAbsoluteFileUnix() {
		assertTrue(checker.check("/a/b"));
	}

	@Test
	public void testLocalAbsoluteFileWindows() {
		Assume.assumeTrue(SystemReader.getInstance().isWindows());
		assertTrue(checker.check("C:/a/b"));
	}

	@Test
	public void testLocalRelativePath() {
		assertFalse(checker.check("a/b"));
	}

	@Test
	public void testSSH() {
		assertFalse(checker.check("ssh://a.test:/a/b"));
		assertFalse(checker.check("joe@a.test:/a/b"));
	}

	@Test
	public void testHTTP() {
		assertFalse(checker.check("https://a.test/a/b"));
	}

	@Test
	public void testFile() {
		assertFalse(checker.check("file://a.test/a/b"));
	}

	@Test
	public void testFooBar() {
		assertFalse(checker.check("foobar://a.test/a/b"));
	}
}
