package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.jgit.api.CheckoutCommand.Stage;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class PathCheckoutCommandTest extends RepositoryTestCase {

	private static final String FILE1 = "f/Test.txt";

	private static final String FILE2 = "Test2.txt";

	private static final String FILE3 = "Test3.txt";

	private static final String LINK = "link";

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
	public void testUpdateSymLink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());

		Path path = writeLink(LINK
		git.add().addFilepattern(LINK).call();
		git.commit().setMessage("Added link").call();
		assertEquals("3"

		writeLink(LINK
		assertEquals("c"

		CheckoutCommand co = git.checkout();
		co.addPath(LINK).call();

		assertEquals("3"
	}

	@Test
	public void testUpdateBrokenSymLinkToDirectory() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());

		Path path = writeLink(LINK
		git.add().addFilepattern(LINK).call();
		git.commit().setMessage("Added link").call();
		assertEquals("f"
		assertTrue(path.toFile().exists());

		writeLink(LINK
		assertFalse(path.toFile().exists());
		assertEquals("link_to_nowhere"

		CheckoutCommand co = git.checkout();
		co.addPath(LINK).call();

		assertEquals("f"
	}

	@Test
	public void testUpdateBrokenSymLink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());

		Path path = writeLink(LINK
		git.add().addFilepattern(LINK).call();
		git.commit().setMessage("Added link").call();
		assertEquals("3"
		assertEquals(FILE1

		writeLink(LINK
		assertFalse(path.toFile().exists());
		assertEquals("link_to_nowhere"

		CheckoutCommand co = git.checkout();
		co.addPath(LINK).call();

		assertEquals("3"
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

	@Test
	public void testUpdateWorkingDirectoryFromIndex2() throws Exception {
		CheckoutCommand co = git.checkout();
		fsTick(git.getRepository().getIndexFile());

		File written1 = writeTrashFile(FILE1
		File written2 = writeTrashFile(FILE2
		fsTick(written2);

		writeTrashFile(FILE3
		git.add().addFilepattern(FILE3).call();
		fsTick(git.getRepository().getIndexFile());

		git.add().addFilepattern(FILE1).addFilepattern(FILE2).call();
		fsTick(git.getRepository().getIndexFile());

		writeTrashFile(FILE1
		writeTrashFile(FILE2
		fsTick(written2);

		co.addPath(FILE1).setStartPoint(secondCommit).call();

		assertEquals("2"
		assertEquals("a(modified again)"

		validateIndex(git);
	}

	public static void validateIndex(Git git) throws NoWorkTreeException
			IOException {
		DirCache dc = git.getRepository().lockDirCache();
		try (ObjectReader r = git.getRepository().getObjectDatabase()
				.newReader()) {
			for (int i = 0; i < dc.getEntryCount(); ++i) {
				DirCacheEntry entry = dc.getEntry(i);
				if (entry.getLength() > 0)
					assertEquals(entry.getLength()
							entry.getObjectId()
			}
		} finally {
			dc.unlock();
		}
	}

	@Test
	public void testCheckoutMixedNewlines() throws Exception {
		StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();
		File written = writeTrashFile(FILE1
		assertEquals("4\r\n4"
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("CRLF").call();
		written = writeTrashFile(FILE1
		assertEquals("4\n4"
		git.add().addFilepattern(FILE1).call();
		git.checkout().addPath(FILE1).call();
		Status status = git.status().call();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testCheckoutRepository() throws Exception {
		CheckoutCommand co = git.checkout();
		File test = writeTrashFile(FILE1
		File test2 = writeTrashFile(FILE2
		co.setStartPoint("HEAD~2").setAllPaths(true).call();
		assertEquals("1"
		assertEquals("a"
	}


	@Test(expected = JGitInternalException.class)
	public void testCheckoutOfConflictingFileShouldThrow()
			throws Exception {
		setupConflictingState();

		git.checkout().addPath(FILE1).call();
	}

	@Test
	public void testCheckoutOurs() throws Exception {
		setupConflictingState();

		git.checkout().setStage(Stage.OURS).addPath(FILE1).call();

		assertEquals("3"
		assertStageOneToThree(FILE1);
	}

	@Test
	public void testCheckoutTheirs() throws Exception {
		setupConflictingState();

		git.checkout().setStage(Stage.THEIRS).addPath(FILE1).call();

		assertEquals("Conflicting"
		assertStageOneToThree(FILE1);
	}

	@Test
	public void testCheckoutOursWhenNoBase() throws Exception {
		String file = "added.txt";

		git.checkout().setCreateBranch(true).setName("side")
				.setStartPoint(initialCommit).call();
		writeTrashFile(file
		git.add().addFilepattern(file).call();
		RevCommit side = git.commit().setMessage("Commit on side").call();

		git.checkout().setName("master").call();
		writeTrashFile(file
		git.add().addFilepattern(file).call();
		git.commit().setMessage("Commit on master").call();

		git.merge().include(side).call();
		assertEquals(RepositoryState.MERGING

		DirCache cache = DirCache.read(db.getIndexFile()
		assertEquals("Expected add/add file to not have base stage"
				DirCacheEntry.STAGE_2

		assertTrue(read(file).startsWith("<<<<<<< HEAD"));

		git.checkout().setStage(Stage.OURS).addPath(file).call();

		assertEquals("Added on master"

		cache = DirCache.read(db.getIndexFile()
		assertEquals("Expected conflict stages to still exist after checkout"
				DirCacheEntry.STAGE_2
	}

	@Test(expected = IllegalStateException.class)
	public void testStageNotPossibleWithBranch() throws Exception {
		git.checkout().setStage(Stage.OURS).setStartPoint("master").call();
	}

	private void setupConflictingState() throws Exception {
		git.checkout().setCreateBranch(true).setName("conflict")
				.setStartPoint(initialCommit).call();
		writeTrashFile(FILE1
		RevCommit conflict = git.commit().setAll(true)
				.setMessage("Conflicting change").call();

		git.checkout().setName("master").call();

		git.merge().include(conflict).call();
		assertEquals(RepositoryState.MERGING
		assertStageOneToThree(FILE1);
	}

	private void assertStageOneToThree(String name) throws Exception {
		DirCache cache = DirCache.read(db.getIndexFile()
		int i = cache.findEntry(name);
		DirCacheEntry stage1 = cache.getEntry(i);
		DirCacheEntry stage2 = cache.getEntry(i + 1);
		DirCacheEntry stage3 = cache.getEntry(i + 2);

		assertEquals(DirCacheEntry.STAGE_1
		assertEquals(DirCacheEntry.STAGE_2
		assertEquals(DirCacheEntry.STAGE_3
	}
}
