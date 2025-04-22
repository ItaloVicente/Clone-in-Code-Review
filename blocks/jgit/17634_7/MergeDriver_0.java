package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.merge.MergeDriver;
import org.eclipse.jgit.merge.MergeDriverRegistry;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MergeDriverTest extends RepositoryTestCase {
	private Git git;

	private RevCommit branchCommit;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		writeTrashFile("d.txt"
		git.add().addFilepattern("b.txt").addFilepattern("c.txt").addFilepattern("d.txt").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		createBranch(initialCommit

		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("d.txt"
		git.rm().addFilepattern("c.txt").call();
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").addFilepattern("d.txt").call();
		git.commit().setMessage("master").call();

		checkoutBranch("refs/heads/side");

		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		git.rm().addFilepattern("d.txt").call();
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").addFilepattern("c.txt").call();
		branchCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
	}

	@Override
	@After
	public void tearDown() throws Exception {
		MergeDriverRegistry.clear();
		super.tearDown();
	}

	@Test
	public void testDriverAssociation() {
		MergeDriver failing = new FailingDriver();
		MergeDriver ours = new Ours();
		MergeDriver theirs = new Theirs();

		MergeDriverRegistry.registerDriver(failing);
		MergeDriverRegistry.registerDriver(ours);
		MergeDriverRegistry.registerDriver(theirs);

		assertNull(MergeDriverRegistry.findMergeDriver("a.txt"));

		String namePattern = "*.txt";
		MergeDriverRegistry.associate(namePattern
		assertSame(failing

		MergeDriverRegistry.associate(namePattern
		assertSame(ours

		assertNull(MergeDriverRegistry.findMergeDriver("a"));

		MergeDriverRegistry.associate("abc"
		assertNull(MergeDriverRegistry.findMergeDriver("abc"));
	}

	@Test
	public void testOursMerge() throws Exception {

		MergeDriver driver = new Ours();
		MergeDriverRegistry.registerDriver(driver);
		MergeDriverRegistry.associate("*.txt"
		MergeResult result = git.merge().include(branchCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertEquals("new ours file"
		assertEquals("changed ours content"

		assertEquals(2
		assertTrue(result.getConflicts().containsKey("c.txt"));
		assertTrue(result.getConflicts().containsKey("d.txt"));

		assertEquals(RepositoryState.MERGING
	}

	@Test
	public void testTheirsMerge() throws Exception {

		MergeDriver driver = new Theirs();
		MergeDriverRegistry.registerDriver(driver);
		MergeDriverRegistry.associate("*.txt"
		MergeResult result = git.merge().include(branchCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertEquals("new theirs file"
		assertEquals("changed theirs content"

		assertEquals(2
		assertTrue(result.getConflicts().containsKey("c.txt"));
		assertTrue(result.getConflicts().containsKey("d.txt"));

		assertEquals(RepositoryState.MERGING
	}

	private static class Theirs implements MergeDriver {
		public boolean merge(Config configuration
				InputStream theirs
				String[] commitNames) throws IOException {
			byte[] buffer = new byte[8192];
			int read = theirs.read(buffer);
			while (read > 0) {
				output.write(buffer
				read = theirs.read(buffer);
			}

			return true;
		}

		public String getName() {
			return "theirs";
		}
	}

	private static class Ours implements MergeDriver {
		public boolean merge(Config configuration
				InputStream theirs
				String[] commitNames) throws IOException {
			byte[] buffer = new byte[8192];
			int read = ours.read(buffer);
			while (read > 0) {
				output.write(buffer
				read = ours.read(buffer);
			}

			return true;
		}

		public String getName() {
			return "ours";
		}
	}

	private static class FailingDriver implements MergeDriver {
		public boolean merge(Config configuration
				InputStream theirs
				String[] commitNames) throws IOException {
			throw new RuntimeException();
		}

		public String getName() {
			return "failing";
		}
	}
}
