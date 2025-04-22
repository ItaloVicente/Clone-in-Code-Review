package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class PathCheckoutCommandTest extends RepositoryTestCase {

	Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		writeTrashFile("Test2.txt"
		git.add().addFilepattern("Test.txt").addFilepattern("Test2.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();

		writeTrashFile("Test.txt"
		writeTrashFile("Test2.txt"
		git.add().addFilepattern("Test.txt").addFilepattern("Test2.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();

		writeTrashFile("Test.txt"
		writeTrashFile("Test2.txt"
		git.add().addFilepattern("Test.txt").addFilepattern("Test2.txt").call();
		git.commit().setMessage("Third commit").call();
	}

	@Test
	public void testUpdateWorkingDirectory() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile("Test.txt"
		assertEquals(""
		co.addPath("Test.txt").call();
		assertEquals("3"
		assertEquals("c"
	}

	@Test
	public void testCheckoutFirst() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile("Test.txt"
		co.setStartPoint(initialCommit).addPath("Test.txt").call();
		assertEquals("1"
		assertEquals("c"
	}

	@Test
	public void testCheckoutSecond() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile("Test.txt"
		co.setStartPoint("HEAD~1").addPath("Test.txt").call();
		assertEquals("2"
		assertEquals("c"
	}

	@Test
	public void testCheckoutMultiple() throws Exception {
		CheckoutCommand co = git.checkout();
		File test = writeTrashFile("Test.txt"
		File test2 = writeTrashFile("Test2.txt"
		co.setStartPoint("HEAD~2").addPath("Test.txt").addPath("Test2.txt")
				.call();
		assertEquals("1"
		assertEquals("a"
	}
}
