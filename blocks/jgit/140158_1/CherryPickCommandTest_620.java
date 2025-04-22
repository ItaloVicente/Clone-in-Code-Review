package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.CheckoutResult.Status;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.BuiltinLFS;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Sets;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class CheckoutCommandTest extends RepositoryTestCase {
	private Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		BuiltinLFS.register();
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();

		git.branchCreate().setName("test").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test");

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();
	}

	@Test
	public void testSimpleCheckout() throws Exception {
		git.checkout().setName("test").call();
	}

	@Test
	public void testCheckout() throws Exception {
		git.checkout().setName("test").call();
		assertEquals("[Test.txt
				indexState(CONTENT));
		Ref result = git.checkout().setName("master").call();
		assertEquals("[Test.txt
				indexState(CONTENT));
		assertEquals("refs/heads/master"
		assertEquals("refs/heads/master"
	}

	@Test
	public void testCheckoutForced() throws Exception {
		writeTrashFile("Test.txt"
		try {
			git.checkout().setName("master").call().getObjectId();
			fail("Expected CheckoutConflictException didn't occur");
		} catch (CheckoutConflictException e) {
		}
		assertEquals(initialCommit.getId()
				.setForced(true).call().getObjectId());
	}

	@Test
	public void testCreateBranchOnCheckout() throws Exception {
		git.checkout().setCreateBranch(true).setName("test2").call();
		assertNotNull(db.exactRef("refs/heads/test2"));
	}

	@Test
	public void testCheckoutToNonExistingBranch() throws GitAPIException {
		try {
			git.checkout().setName("badbranch").call();
			fail("Should have failed");
		} catch (RefNotFoundException e) {
		}
	}

	@Test
	public void testCheckoutWithConflict() throws Exception {
		CheckoutCommand co = git.checkout();
		try {
			writeTrashFile("Test.txt"
			assertEquals(Status.NOT_TRIED
			co.setName("master").call();
			fail("Should have failed");
		} catch (Exception e) {
			assertEquals(Status.CONFLICTS
			assertTrue(co.getResult().getConflictList().contains("Test.txt"));
		}
		git.checkout().setName("master").setForced(true).call();
		assertThat(read("Test.txt")
	}

	@Test
	public void testCheckoutWithNonDeletedFiles() throws Exception {
		File testFile = writeTrashFile("temp"
		try (FileInputStream fis = new FileInputStream(testFile)) {
			FileUtils.delete(testFile);
			return;
		} catch (IOException e) {
		}
		FileUtils.delete(testFile);
		CheckoutCommand co = git.checkout();
		testFile = new File(db.getWorkTree()
		assertTrue(testFile.exists());
		FileUtils.delete(testFile);
		assertFalse(testFile.exists());
		git.add().addFilepattern("Test.txt");
		git.commit().setMessage("Delete Test.txt").setAll(true).call();
		git.checkout().setName("master").call();
		assertTrue(testFile.exists());
		try (FileInputStream fis = new FileInputStream(testFile)) {
			assertEquals(Status.NOT_TRIED
			co.setName("test").call();
			assertTrue(testFile.exists());
			assertEquals(Status.NONDELETED
			assertTrue(co.getResult().getUndeletedList().contains("Test.txt"));
		}
	}

	@Test
	public void testCheckoutCommit() throws Exception {
		Ref result = git.checkout().setName(initialCommit.name()).call();
		assertEquals("[Test.txt
				indexState(CONTENT));
		assertNull(result);
		assertEquals(initialCommit.name()
	}

	@Test
	public void testCheckoutLightweightTag() throws Exception {
		git.tag().setAnnotated(false).setName("test-tag")
				.setObjectId(initialCommit).call();
		Ref result = git.checkout().setName("test-tag").call();

		assertNull(result);
		assertEquals(initialCommit.getId()
		assertHeadDetached();
	}

	@Test
	public void testCheckoutAnnotatedTag() throws Exception {
		git.tag().setAnnotated(true).setName("test-tag")
				.setObjectId(initialCommit).call();
		Ref result = git.checkout().setName("test-tag").call();

		assertNull(result);
		assertEquals(initialCommit.getId()
		assertHeadDetached();
	}

	@Test
	public void testCheckoutRemoteTrackingWithUpstream() throws Exception {
		Repository db2 = createRepositoryWithRemote();

		Git.wrap(db2).checkout().setCreateBranch(true).setName("test")
				.setStartPoint("origin/test")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();

		assertEquals("refs/heads/test"
				db2.exactRef(Constants.HEAD).getTarget().getName());
		StoredConfig config = db2.getConfig();
		assertEquals("origin"
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REMOTE));
		assertEquals("refs/heads/test"
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGE));
	}

	@Test
	public void testCheckoutRemoteTrackingWithoutLocalBranch() throws Exception {
		Repository db2 = createRepositoryWithRemote();

		Git.wrap(db2).checkout().setName("remotes/origin/test").call();
		assertEquals("[Test.txt
				indexState(db2
	}



	@Test
	public void testCheckoutOfFileWithInexistentParentDir() throws Exception {
		File a = writeTrashFile("dir/a.txt"
		writeTrashFile("dir/b.txt"
		git.add().addFilepattern("dir/a.txt").addFilepattern("dir/b.txt")
				.call();
		git.commit().setMessage("Added dir").call();

		File dir = new File(db.getWorkTree()
		FileUtils.delete(dir

		git.checkout().addPath("dir/a.txt").call();
		assertTrue(a.exists());
	}

	@Test
	public void testCheckoutOfDirectoryShouldBeRecursive() throws Exception {
		File a = writeTrashFile("dir/a.txt"
		File b = writeTrashFile("dir/sub/b.txt"
		git.add().addFilepattern("dir").call();
		git.commit().setMessage("Added dir").call();

		write(a
		write(b
		git.checkout().addPath("dir").call();

		assertThat(read(a)
		assertThat(read(b)
	}

	@Test
	public void testCheckoutAllPaths() throws Exception {
		File a = writeTrashFile("dir/a.txt"
		File b = writeTrashFile("dir/sub/b.txt"
		git.add().addFilepattern("dir").call();
		git.commit().setMessage("Added dir").call();

		write(a
		write(b
		git.checkout().setAllPaths(true).call();

		assertThat(read(a)
		assertThat(read(b)
	}

	@Test
	public void testCheckoutWithStartPoint() throws Exception {
		File a = writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		RevCommit first = git.commit().setMessage("Added a").call();

		write(a
		git.commit().setAll(true).setMessage("Other").call();

		git.checkout().setCreateBranch(true).setName("a")
				.setStartPoint(first.getId().getName()).call();

		assertThat(read(a)
	}

	@Test
	public void testCheckoutWithStartPointOnlyCertainFiles() throws Exception {
		File a = writeTrashFile("a.txt"
		File b = writeTrashFile("b.txt"
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		RevCommit first = git.commit().setMessage("First").call();

		write(a
		write(b
		git.commit().setAll(true).setMessage("Other").call();

		git.checkout().setCreateBranch(true).setName("a")
				.setStartPoint(first.getId().getName()).addPath("a.txt").call();

		assertThat(read(a)
		assertThat(read(b)
	}

	@Test
	public void testDetachedHeadOnCheckout() throws JGitInternalException
			IOException
		CheckoutCommand co = git.checkout();
		co.setName("master").call();

		String commitId = db.exactRef(R_HEADS + MASTER).getObjectId().name();
		co = git.checkout();
		co.setName(commitId).call();

		assertHeadDetached();
	}

	@Test
	public void testUpdateSmudgedEntries() throws Exception {
		git.branchCreate().setName("test2").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test2");

		File file = new File(db.getWorkTree()
		long size = file.length();
		long mTime = file.lastModified() - 5000L;
		assertTrue(file.setLastModified(mTime));

		DirCache cache = DirCache.lock(db.getIndexFile()
		DirCacheEntry entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		entry.setLength(0);
		entry.setLastModified(0);
		cache.write();
		assertTrue(cache.commit());

		cache = DirCache.read(db.getIndexFile()
		entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		assertEquals(0
		assertEquals(0

		db.getIndexFile().setLastModified(
				db.getIndexFile().lastModified() - 5000);

		assertNotNull(git.checkout().setName("test").call());

		cache = DirCache.read(db.getIndexFile()
		entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		assertEquals(size
		assertEquals(mTime
	}

	@Test
	public void testCheckoutOrphanBranch() throws Exception {
		CheckoutCommand co = newOrphanBranchCommand();
		assertCheckoutRef(co.call());

		File HEAD = new File(trash
		String headRef = read(HEAD);
		assertEquals("ref: refs/heads/orphanbranch\n"
		assertEquals(2

		File heads = new File(trash
		assertEquals(2

		this.assertNoHead();
		this.assertRepositoryCondition(1);
		assertEquals(CheckoutResult.NOT_TRIED_RESULT
	}

	private Repository createRepositoryWithRemote() throws IOException
			URISyntaxException
			InvalidRemoteException
		Repository db2 = createWorkRepository();
		try (Git git2 = new Git(db2)) {
			final StoredConfig config = db2.getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(db.getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.update(config);
			config.save();

			git2.fetch().setRemote("origin")
			return db2;
		}
	}

	private CheckoutCommand newOrphanBranchCommand() {
		return git.checkout().setOrphan(true)
				.setName("orphanbranch");
	}

	private static void assertCheckoutRef(Ref ref) {
		assertNotNull(ref);
		assertEquals("refs/heads/orphanbranch"
	}

	private void assertNoHead() throws IOException {
		assertNull(db.resolve("HEAD"));
	}

	private void assertHeadDetached() throws IOException {
		Ref head = db.exactRef(Constants.HEAD);
		assertFalse(head.isSymbolic());
		assertSame(head
	}

	private void assertRepositoryCondition(int files) throws GitAPIException {
		org.eclipse.jgit.api.Status status = this.git.status().call();
		assertFalse(status.isClean());
		assertEquals(files
	}

	@Test
	public void testCreateOrphanBranchWithStartCommit() throws Exception {
		CheckoutCommand co = newOrphanBranchCommand();
		Ref ref = co.setStartPoint(initialCommit).call();
		assertCheckoutRef(ref);
		assertEquals(2
		this.assertNoHead();
		this.assertRepositoryCondition(1);
	}

	@Test
	public void testCreateOrphanBranchWithStartPoint() throws Exception {
		CheckoutCommand co = newOrphanBranchCommand();
		Ref ref = co.setStartPoint("HEAD^").call();
		assertCheckoutRef(ref);

		assertEquals(2
		this.assertNoHead();
		this.assertRepositoryCondition(1);
	}

	@Test
	public void testInvalidRefName() throws Exception {
		try {
			git.checkout().setOrphan(true).setName("../invalidname").call();
			fail("Should have failed");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void testNullRefName() throws Exception {
		try {
			git.checkout().setOrphan(true).setName(null).call();
			fail("Should have failed");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void testAlreadyExists() throws Exception {
		this.git.checkout().setCreateBranch(true).setName("orphanbranch")
				.call();
		this.git.checkout().setName("master").call();

		try {
			newOrphanBranchCommand().call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
	}

	@Test
	public void testCheckoutAutoCrlfTrue() throws Exception {
		int nrOfAutoCrlfTestFiles = 200;

		FileBasedConfig c = db.getConfig();
		c.setString("core"
		c.save();

		AddCommand add = git.add();
		for (int i = 100; i < 100 + nrOfAutoCrlfTestFiles; i++) {
			writeTrashFile("Test_" + i + ".txt"
					+ " world\nX\nYU\nJK\n");
			add.addFilepattern("Test_" + i + ".txt");
		}
		fsTick(null);
		add.call();
		RevCommit c1 = git.commit().setMessage("add some lines").call();

		add = git.add();
		for (int i = 100; i < 100 + nrOfAutoCrlfTestFiles; i++) {
			writeTrashFile("Test_" + i + ".txt"
					+ " world\nX\nY\n");
			add.addFilepattern("Test_" + i + ".txt");
		}
		fsTick(null);
		add.call();
		git.commit().setMessage("add more").call();

		git.checkout().setName(c1.getName()).call();

		boolean foundUnsmudged = false;
		DirCache dc = db.readDirCache();
		for (int i = 100; i < 100 + nrOfAutoCrlfTestFiles; i++) {
			DirCacheEntry entry = dc.getEntry(
					"Test_" + i + ".txt");
			if (!entry.isSmudged()) {
				foundUnsmudged = true;
				assertEquals("unexpected file length in git index"
						entry.getLength());
			}
		}
		org.junit.Assume.assumeTrue(foundUnsmudged);
	}

	@Test
	public void testSmudgeFilter_modifyExisting() throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content1 = git.commit().setMessage("add content").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content2 = git.commit().setMessage("changed content").call();

		git.checkout().setName(content1.getName()).call();
		git.checkout().setName(content2.getName()).call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals(Sets.of("src/a.txt")
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_createNew()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		RevCommit initial = git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content = git.commit().setMessage("added content").call();

		git.checkout().setName(initial.getName()).call();
		git.checkout().setName(content.getName()).call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_deleteFileAndRestoreFromCommit()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content = git.commit().setMessage("added content").call();

		deleteTrashFile("src/a.txt");
		git.checkout().setStartPoint(content.getName()).addPath("src/a.txt")
				.call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_deleteFileAndRestoreFromIndex()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		git.commit().setMessage("added content").call();

		deleteTrashFile("src/a.txt");
		git.checkout().addPath("src/a.txt").call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_deleteFileAndCreateBranchAndRestoreFromCommit()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content = git.commit().setMessage("added content").call();

		deleteTrashFile("src/a.txt");
		git.checkout().setName("newBranch").setCreateBranch(true)
				.setStartPoint(content).addPath("src/a.txt").call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeAndClean() throws Exception {
		File clean_filter = writeTempFile("sed s/V1/@version/g");
		File smudge_filter = writeTempFile("sed s/@version/V1/g");

		try (Git git2 = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(smudge_filter.getPath()));
			config.setString("filter"
					"sh " + slashify(clean_filter.getPath()));
			config.setBoolean("filter"
			config.save();
			writeTrashFile(".gitattributes"
			git2.add().addFilepattern(".gitattributes").call();
			git2.commit().setMessage("add attributes").call();

			fsTick(writeTrashFile("filterTest.txt"
			git2.add().addFilepattern("filterTest.txt").call();
			RevCommit one = git2.commit().setMessage("add filterText.txt").call();
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			fsTick(writeTrashFile("filterTest.txt"
			git2.add().addFilepattern("filterTest.txt").call();
			RevCommit two = git2.commit().setMessage("modified filterTest.txt").call();

			assertTrue(git2.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			git2.checkout().setName(one.getName()).call();
			assertTrue(git2.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("hello world

			git2.checkout().setName(two.getName()).call();
			assertTrue(git2.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("bon giorno world
		}
	}

	@Test
	public void testNonDeletableFilesOnWindows()
			throws GitAPIException
		org.junit.Assume.assumeTrue(SystemReader.getInstance().isWindows());
		writeTrashFile("toBeModified.txt"
		writeTrashFile("toBeDeleted.txt"
		git.add().addFilepattern(".").call();
		RevCommit addFiles = git.commit().setMessage("add more files").call();

		git.rm().setCached(false).addFilepattern("Test.txt")
				.addFilepattern("toBeDeleted.txt").call();
		writeTrashFile("toBeModified.txt"
		writeTrashFile("toBeCreated.txt"
		git.add().addFilepattern(".").call();
		RevCommit crudCommit = git.commit().setMessage("delete
				.call();
		git.checkout().setName(addFiles.getName()).call();
		try ( FileInputStream fis=new FileInputStream(new File(db.getWorkTree()
			CheckoutCommand coCommand = git.checkout();
			coCommand.setName(crudCommit.getName()).call();
			CheckoutResult result = coCommand.getResult();
			assertEquals(Status.NONDELETED
			assertEquals("[Test.txt
					result.getRemovedList().toString());
			assertEquals("[toBeCreated.txt
					result.getModifiedList().toString());
			assertEquals("[Test.txt]"
			assertTrue(result.getConflictList().isEmpty());
		}
	}

	private File writeTempFile(String body) throws IOException {
		File f = File.createTempFile("CheckoutCommandTest_"
		JGitTestUtil.write(f
		return f;
	}
}
