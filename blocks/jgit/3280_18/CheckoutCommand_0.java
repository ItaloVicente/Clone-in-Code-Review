package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class PathCheckoutCommandTest extends RepositoryTestCase {

	private static final String FILE1 = "f/Test.txt";

	private static final String FILE2 = "Test2.txt";

	Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile(FILE1
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE1).addFilepattern(FILE2).call();
		initialCommit = git.commit().setMessage("Initial commit").call();
		writeTrashFile(FILE1
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE1).addFilepattern(FILE2).call();
		secondCommit = git.commit().setMessage("Second commit").call();
		writeTrashFile(FILE1
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE1).addFilepattern(FILE2).call();
		git.commit().setMessage("Third commit").call();
	}

	@Test
	public void testUpdateWorkingDirectory() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile(FILE1
		assertEquals(""
		co.addPath(FILE1).call();
		assertEquals("3"
		assertEquals("c"
	}

	@Test
	public void testCheckoutFirst() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile(FILE1
		co.setStartPoint(initialCommit).addPath(FILE1).call();
		assertEquals("1"
		assertEquals("c"
	}

	@Test
	public void testCheckoutSecond() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile(FILE1
		co.setStartPoint("HEAD~1").addPath(FILE1).call();
		assertEquals("2"
		assertEquals("c"
	}

	@Test
	public void testCheckoutMultiple() throws Exception {
		CheckoutCommand co = git.checkout();
		File test = writeTrashFile(FILE1
		File test2 = writeTrashFile(FILE2
		co.setStartPoint("HEAD~2").addPath(FILE1).addPath(FILE2).call();
		assertEquals("1"
		assertEquals("a"
	}

	@Test
	public void testUpdateWorkingDirectoryFromIndex() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		written = writeTrashFile(FILE1
		assertEquals(""
		co.addPath(FILE1).call();
		assertEquals("3a"
		assertEquals("c"
	}

	@Test
	public void testUpdateWorkingDirectoryFromHeadWithIndexChange()
			throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		written = writeTrashFile(FILE1
		assertEquals(""
		co.addPath(FILE1).setStartPoint("HEAD").call();
		assertEquals("3"
		assertEquals("c"
	}

}
