package org.eclipse.jgit.api;

import static org.eclipse.jgit.api.ResetCommand.ResetType.HARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class ResetCommandTest extends RepositoryTestCase {

	private Git git;

	private RevCommit initialCommit;

	private RevCommit secondCommit;

	private File indexFile;

	private File indexNestedFile;

	private File untrackedFile;

	private DirCacheEntry prestage;

	public void setupRepository() throws IOException
			GitAPIException {

		git = new Git(db);
		initialCommit = git.commit().setMessage("initial commit").call();

		indexFile = writeTrashFile("a.txt"

		indexNestedFile = writeTrashFile("dir/b.txt"

		git.add().addFilepattern("a.txt").addFilepattern("dir/b.txt").call();
		secondCommit = git.commit().setMessage("adding a.txt and dir/b.txt").call();

		prestage = DirCache.read(db.getIndexFile()

		writeTrashFile("a.txt"
		writeTrashFile("dir/b.txt"
		git.add().addFilepattern("a.txt").addFilepattern("dir/b.txt").call();

		untrackedFile = writeTrashFile("notAddedToIndex.txt"
	}

	@Test
	public void testHardReset() throws JGitInternalException
			AmbiguousObjectException
		setupRepository();
		ObjectId prevHead = db.resolve(Constants.HEAD);
		ResetCommand reset = git.reset();
		assertSameAsHead(reset.setMode(ResetType.HARD)
				.setRef(initialCommit.getName()).call());
		assertFalse("reflog should be enabled"
		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(initialCommit
		assertFalse(indexFile.exists());
		assertFalse(indexNestedFile.exists());
		assertTrue(untrackedFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertFalse(inIndex(indexFile.getName()));
		assertReflog(prevHead
		assertEquals(prevHead
	}

	@Test
	public void testHardResetReflogDisabled() throws Exception {
		setupRepository();
		ObjectId prevHead = db.resolve(Constants.HEAD);
		ResetCommand reset = git.reset();
		assertSameAsHead(reset.setMode(ResetType.HARD)
				.setRef(initialCommit.getName()).disableRefLog(true).call());
		assertTrue("reflog should be disabled"
		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(initialCommit
		assertFalse(indexFile.exists());
		assertFalse(indexNestedFile.exists());
		assertTrue(untrackedFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertFalse(inIndex(indexFile.getName()));
		assertReflogDisabled(head);
		assertEquals(prevHead
	}

	@Test
	public void testHardResetWithConflicts_OverwriteUntrackedFile() throws Exception {
		setupRepository();

		git.rm().setCached(true).addFilepattern("a.txt").call();
		assertTrue(new File(db.getWorkTree()

		git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD).call();
		assertTrue(new File(db.getWorkTree()
		assertEquals("content"
	}

	@Test
	public void testHardResetWithConflicts_DeleteFileFolderConflict() throws Exception {
		setupRepository();

		writeTrashFile("dir-or-file/c.txt"
		git.add().addFilepattern("dir-or-file/c.txt").call();

		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("dir-or-file"

		git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD).call();
		assertFalse(new File(db.getWorkTree()
	}

	@Test
	public void testResetToNonexistingHEAD() throws JGitInternalException
			AmbiguousObjectException

		git = new Git(db);
		writeTrashFile("f"

		try {
			git.reset().setRef(Constants.HEAD).call();
			fail("Expected JGitInternalException didn't occur");
		} catch (JGitInternalException e) {
		}
	}

	@Test
	public void testSoftReset() throws JGitInternalException
			AmbiguousObjectException
		setupRepository();
		ObjectId prevHead = db.resolve(Constants.HEAD);
		assertSameAsHead(git.reset().setMode(ResetType.SOFT)
				.setRef(initialCommit.getName()).call());
		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(initialCommit
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertTrue(inIndex(indexFile.getName()));
		assertReflog(prevHead
		assertEquals(prevHead
	}

	@Test
	public void testMixedReset() throws JGitInternalException
			AmbiguousObjectException
		setupRepository();
		ObjectId prevHead = db.resolve(Constants.HEAD);
		assertSameAsHead(git.reset().setMode(ResetType.MIXED)
				.setRef(initialCommit.getName()).call());
		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(initialCommit
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertFalse(inIndex(indexFile.getName()));

		assertReflog(prevHead
		assertEquals(prevHead
	}

	@Test
	public void testMixedResetRetainsSizeAndModifiedTime() throws Exception {
		git = new Git(db);

		writeTrashFile("a.txt"
				System.currentTimeMillis() - 60 * 1000);
		assertNotNull(git.add().addFilepattern("a.txt").call());
		assertNotNull(git.commit().setMessage("a commit").call());

		writeTrashFile("b.txt"
				System.currentTimeMillis() - 60 * 1000);
		assertNotNull(git.add().addFilepattern("b.txt").call());
		RevCommit commit2 = git.commit().setMessage("b commit").call();
		assertNotNull(commit2);

		DirCache cache = db.readDirCache();

		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNotNull(aEntry);
		assertTrue(aEntry.getLength() > 0);
		assertTrue(aEntry.getLastModified() > 0);

		DirCacheEntry bEntry = cache.getEntry("b.txt");
		assertNotNull(bEntry);
		assertTrue(bEntry.getLength() > 0);
		assertTrue(bEntry.getLastModified() > 0);

		assertSameAsHead(git.reset().setMode(ResetType.MIXED)
				.setRef(commit2.getName()).call());

		cache = db.readDirCache();

		DirCacheEntry mixedAEntry = cache.getEntry("a.txt");
		assertNotNull(mixedAEntry);
		assertEquals(aEntry.getLastModified()
		assertEquals(aEntry.getLastModified()

		DirCacheEntry mixedBEntry = cache.getEntry("b.txt");
		assertNotNull(mixedBEntry);
		assertEquals(bEntry.getLastModified()
		assertEquals(bEntry.getLastModified()
	}

	@Test
	public void testMixedResetWithUnmerged() throws Exception {
		git = new Git(db);

		String file = "a.txt";
		writeTrashFile(file
		String file2 = "b.txt";
		writeTrashFile(file2

		git.add().addFilepattern(file).addFilepattern(file2).call();
		git.commit().setMessage("commit").call();

		DirCache index = db.lockDirCache();
		DirCacheBuilder builder = index.builder();
		builder.add(createEntry(file
		builder.add(createEntry(file
		builder.add(createEntry(file
		assertTrue(builder.commit());

		assertEquals("[a.txt
				+ "[a.txt
				+ "[a.txt
				indexState(0));

		assertSameAsHead(git.reset().setMode(ResetType.MIXED).call());

		assertEquals("[a.txt
				indexState(0));
	}

	@Test
	public void testPathsReset() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		assertSameAsHead(git.reset().addPath(indexFile.getName())
				.addPath(untrackedFile.getName()).call());

		DirCacheEntry postReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(postReset);
		Assert.assertNotSame(preReset.getObjectId()
		Assert.assertEquals(prestage.getObjectId()

		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(secondCommit
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		assertTrue(inHead(indexFile.getName()));
		assertTrue(inIndex(indexFile.getName()));
		assertFalse(inIndex(untrackedFile.getName()));
	}

	@Test
	public void testPathsResetOnDirs() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry("dir/b.txt");
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		assertSameAsHead(git.reset().addPath("dir").call());

		DirCacheEntry postReset = DirCache.read(db.getIndexFile()
				.getEntry("dir/b.txt");
		assertNotNull(postReset);
		Assert.assertNotSame(preReset.getObjectId()

		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(secondCommit
		assertTrue(untrackedFile.exists());
		assertTrue(inHead("dir/b.txt"));
		assertTrue(inIndex("dir/b.txt"));
	}

	@Test
	public void testPathsResetWithRef() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		assertSameAsHead(git.reset().setRef(initialCommit.getName())
				.addPath(indexFile.getName()).addPath(untrackedFile.getName())
				.call());

		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(secondCommit
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		assertTrue(inHead(indexFile.getName()));
		assertFalse(inIndex(indexFile.getName()));
		assertFalse(inIndex(untrackedFile.getName()));
	}

	@Test
	public void testPathsResetWithUnmerged() throws Exception {
		setupRepository();

		String file = "a.txt";
		writeTrashFile(file

		git.add().addFilepattern(file).call();
		git.commit().setMessage("commit").call();

		DirCache index = db.lockDirCache();
		DirCacheBuilder builder = index.builder();
		builder.add(createEntry(file
		builder.add(createEntry(file
		builder.add(createEntry(file
		builder.add(createEntry("b.txt"
		assertTrue(builder.commit());

		assertEquals("[a.txt
				+ "[a.txt
				+ "[a.txt
				+ "[b.txt
				indexState(0));

		assertSameAsHead(git.reset().addPath(file).call());

		assertEquals("[a.txt
				indexState(0));
	}

	@Test
	public void testPathsResetOnUnbornBranch() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		assertSameAsHead(git.reset().addPath("a.txt").call());

		DirCache cache = db.readDirCache();
		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNull(aEntry);
	}

	@Test(expected = JGitInternalException.class)
	public void testPathsResetToNonexistingRef() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		assertSameAsHead(
				git.reset().setRef("doesnotexist").addPath("a.txt").call());
	}

	@Test
	public void testResetDefaultMode() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		writeTrashFile("a.txt"
		assertSameAsHead(git.reset().call());

		DirCache cache = db.readDirCache();
		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNull(aEntry);
		assertEquals("modified"
	}

	@Test
	public void testHardResetOnTag() throws Exception {
		setupRepository();
		String tagName = "initialtag";
		git.tag().setName(tagName).setObjectId(secondCommit)
				.setMessage("message").call();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		assertSameAsHead(git.reset().setRef(tagName).setMode(HARD).call());

		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(secondCommit
	}

	@Test
	public void testHardResetAfterSquashMerge() throws Exception {
		git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/master");

		MergeResult result = git.merge()
				.include(db.exactRef("refs/heads/branch1"))
				.setSquash(true)
				.call();

		assertEquals(MergeResult.MergeStatus.FAST_FORWARD_SQUASHED
				result.getMergeStatus());
		assertNotNull(db.readSquashCommitMsg());

		assertSameAsHead(git.reset().setMode(ResetType.HARD)
				.setRef(first.getName()).call());

		assertNull(db.readSquashCommitMsg());
	}

	@Test
	public void testHardResetOnUnbornBranch() throws Exception {
		git = new Git(db);
		File fileA = writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		assertSameAsHead(git.reset().setMode(ResetType.HARD).call());

		DirCache cache = db.readDirCache();
		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNull(aEntry);
		assertFalse(fileA.exists());
		assertNull(db.resolve(Constants.HEAD));
	}

	private void assertReflog(ObjectId prevHead
			throws IOException {
		String actualHeadMessage = db.getReflogReader(Constants.HEAD)
				.getLastEntry().getComment();
		String expectedHeadMessage = head.getName() + ": updating HEAD";
		assertEquals(expectedHeadMessage
		assertEquals(head.getName()
				.getLastEntry().getNewId().getName());
		assertEquals(prevHead.getName()
				.getLastEntry().getOldId().getName());

		String actualMasterMessage = db.getReflogReader("refs/heads/master")
				.getLastEntry().getComment();
		assertEquals(expectedMasterMessage
		assertEquals(head.getName()
				.getLastEntry().getNewId().getName());
		assertEquals(prevHead.getName()
				.getReflogReader("refs/heads/master").getLastEntry().getOldId()
				.getName());
	}

	private void assertReflogDisabled(ObjectId head)
			throws IOException {
		String actualHeadMessage = db.getReflogReader(Constants.HEAD)
				.getLastEntry().getComment();
		String expectedHeadMessage = "commit: adding a.txt and dir/b.txt";
		assertEquals(expectedHeadMessage
		assertEquals(head.getName()
				.getLastEntry().getOldId().getName());

		String actualMasterMessage = db.getReflogReader("refs/heads/master")
				.getLastEntry().getComment();
		String expectedMasterMessage = "commit: adding a.txt and dir/b.txt";
		assertEquals(expectedMasterMessage
		assertEquals(head.getName()
				.getLastEntry().getOldId().getName());
	}
	private boolean inHead(String path) throws IOException {
		ObjectId headId = db.resolve(Constants.HEAD);
		try (RevWalk rw = new RevWalk(db);
				TreeWalk tw = TreeWalk.forPath(db
						rw.parseTree(headId))) {
			return tw != null;
		}
	}

	private boolean inIndex(String path) throws IOException {
		DirCache dc = DirCache.read(db.getIndexFile()
		return dc.getEntry(path) != null;
	}

	private void assertSameAsHead(Ref ref) throws IOException {
		Ref headRef = db.exactRef(Constants.HEAD);
		assertEquals(headRef.getName()
		assertEquals(headRef.getObjectId()
	}
}
