package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.api.CherryPickResult.CherryPickStatus;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.EmptyCommitException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.GpgSigner;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS;
import org.junit.Ignore;
import org.junit.Test;

public class CommitCommandTest extends RepositoryTestCase {

	@Test
	public void testExecutableRetention() throws Exception {
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		config.save();

		FS executableFs = new FS() {

			@Override
			public boolean supportsExecute() {
				return true;
			}

			@Override
			public boolean setExecute(File f
				return true;
			}

			@Override
			public ProcessBuilder runInShell(String cmd
				return null;
			}

			@Override
			public boolean retryFailedLockFileCommit() {
				return false;
			}

			@Override
			public FS newInstance() {
				return this;
			}

			@Override
			protected File discoverGitExe() {
				return null;
			}

			@Override
			public boolean canExecute(File f) {
				return true;
			}

			@Override
			public boolean isCaseSensitive() {
				return true;
			}
		};

		Git git = Git.open(db.getDirectory()
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		RevCommit commit1 = git.commit().setMessage("commit").call();
		TreeWalk walk = TreeWalk.forPath(db
		assertNotNull(walk);
		assertEquals(FileMode.EXECUTABLE_FILE

		FS nonExecutableFs = new FS() {

			@Override
			public boolean supportsExecute() {
				return false;
			}

			@Override
			public boolean setExecute(File f
				return false;
			}

			@Override
			public ProcessBuilder runInShell(String cmd
				return null;
			}

			@Override
			public boolean retryFailedLockFileCommit() {
				return false;
			}

			@Override
			public FS newInstance() {
				return this;
			}

			@Override
			protected File discoverGitExe() {
				return null;
			}

			@Override
			public boolean canExecute(File f) {
				return false;
			}

			@Override
			public boolean isCaseSensitive() {
				return true;
			}
		};

		config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		config.save();

		Git git2 = Git.open(db.getDirectory()
		writeTrashFile(path
		RevCommit commit2 = git2.commit().setOnly(path).setMessage("commit2")
				.call();
		walk = TreeWalk.forPath(db
		assertNotNull(walk);
		assertEquals(FileMode.EXECUTABLE_FILE
	}

	@Test
	public void commitNewSubmodule() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			String path = "sub";
			command.setPath(path);
			String uri = db.getDirectory().toURI().toString();
			command.setURI(uri);
			Repository repo = command.call();
			assertNotNull(repo);
			addRepoToClose(repo);

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
			}
			assertEquals(commit

			RevCommit submoduleCommit = git.commit().setMessage("submodule add")
					.setOnly(path).call();
			assertNotNull(submoduleCommit);
			try (TreeWalk walk = new TreeWalk(db)) {
				walk.addTree(commit.getTree());
				walk.addTree(submoduleCommit.getTree());
				walk.setFilter(TreeFilter.ANY_DIFF);
				List<DiffEntry> diffs = DiffEntry.scan(walk);
				assertEquals(1
				DiffEntry subDiff = diffs.get(0);
				assertEquals(FileMode.MISSING
				assertEquals(FileMode.GITLINK
				assertEquals(ObjectId.zeroId()
				assertEquals(commit
				assertEquals(path
			}
		}
	}

	@Test
	public void commitSubmoduleUpdate() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit2 = git.commit().setMessage("edit file").call();

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			String path = "sub";
			command.setPath(path);
			String uri = db.getDirectory().toURI().toString();
			command.setURI(uri);
			Repository repo = command.call();
			assertNotNull(repo);
			addRepoToClose(repo);

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(commit2
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
			}
			assertEquals(commit2

			RevCommit submoduleAddCommit = git.commit().setMessage("submodule add")
					.setOnly(path).call();
			assertNotNull(submoduleAddCommit);

			RefUpdate update = repo.updateRef(Constants.HEAD);
			update.setNewObjectId(commit);
			assertEquals(Result.FORCED

			RevCommit submoduleEditCommit = git.commit()
					.setMessage("submodule add").setOnly(path).call();
			assertNotNull(submoduleEditCommit);
			try (TreeWalk walk = new TreeWalk(db)) {
				walk.addTree(submoduleAddCommit.getTree());
				walk.addTree(submoduleEditCommit.getTree());
				walk.setFilter(TreeFilter.ANY_DIFF);
				List<DiffEntry> diffs = DiffEntry.scan(walk);
				assertEquals(1
				DiffEntry subDiff = diffs.get(0);
				assertEquals(FileMode.GITLINK
				assertEquals(FileMode.GITLINK
				assertEquals(commit2
				assertEquals(commit
				assertEquals(path
				assertEquals(path
			}
		}
	}

	@Ignore("very flaky when run with Hudson")
	@Test
	public void commitUpdatesSmudgedEntries() throws Exception {
		try (Git git = new Git(db)) {
			File file1 = writeTrashFile("file1.txt"
			assertTrue(file1.setLastModified(file1.lastModified() - 5000));
			File file2 = writeTrashFile("file2.txt"
			assertTrue(file2.setLastModified(file2.lastModified() - 5000));
			File file3 = writeTrashFile("file3.txt"
			assertTrue(file3.setLastModified(file3.lastModified() - 5000));

			assertNotNull(git.add().addFilepattern("file1.txt")
					.addFilepattern("file2.txt").addFilepattern("file3.txt").call());
			RevCommit commit = git.commit().setMessage("add files").call();
			assertNotNull(commit);

			DirCache cache = DirCache.read(db.getIndexFile()
			int file1Size = cache.getEntry("file1.txt").getLength();
			int file2Size = cache.getEntry("file2.txt").getLength();
			int file3Size = cache.getEntry("file3.txt").getLength();
			ObjectId file2Id = cache.getEntry("file2.txt").getObjectId();
			ObjectId file3Id = cache.getEntry("file3.txt").getObjectId();
			assertTrue(file1Size > 0);
			assertTrue(file2Size > 0);
			assertTrue(file3Size > 0);

			cache = DirCache.lock(db.getIndexFile()
			cache.getEntry("file1.txt").setLength(0);
			cache.getEntry("file2.txt").setLength(0);
			cache.getEntry("file3.txt").setLength(0);
			cache.write();
			assertTrue(cache.commit());

			cache = DirCache.read(db.getIndexFile()
			assertEquals(0
			assertEquals(0
			assertEquals(0

			long indexTime = db.getIndexFile().lastModified();
			db.getIndexFile().setLastModified(indexTime - 5000);

			write(file1
			assertTrue(file1.setLastModified(file1.lastModified() + 2500));
			assertNotNull(git.commit().setMessage("edit file").setOnly("file1.txt")
					.call());

			cache = db.readDirCache();
			assertEquals(file1Size
			assertEquals(file2Size
			assertEquals(file3Size
			assertEquals(file2Id
			assertEquals(file3Id
		}
	}

	@Ignore("very flaky when run with Hudson")
	@Test
	public void commitIgnoresSmudgedEntryWithDifferentId() throws Exception {
		try (Git git = new Git(db)) {
			File file1 = writeTrashFile("file1.txt"
			assertTrue(file1.setLastModified(file1.lastModified() - 5000));
			File file2 = writeTrashFile("file2.txt"
			assertTrue(file2.setLastModified(file2.lastModified() - 5000));

			assertNotNull(git.add().addFilepattern("file1.txt")
					.addFilepattern("file2.txt").call());
			RevCommit commit = git.commit().setMessage("add files").call();
			assertNotNull(commit);

			DirCache cache = DirCache.read(db.getIndexFile()
			int file1Size = cache.getEntry("file1.txt").getLength();
			int file2Size = cache.getEntry("file2.txt").getLength();
			assertTrue(file1Size > 0);
			assertTrue(file2Size > 0);

			writeTrashFile("file2.txt"
			assertNotNull(git.add().addFilepattern("file2.txt").call());
			writeTrashFile("file2.txt"

			cache = DirCache.lock(db.getIndexFile()
			cache.getEntry("file1.txt").setLength(0);
			cache.getEntry("file2.txt").setLength(0);
			cache.write();
			assertTrue(cache.commit());

			cache = db.readDirCache();
			assertEquals(0
			assertEquals(0

			long indexTime = db.getIndexFile().lastModified();
			db.getIndexFile().setLastModified(indexTime - 5000);

			write(file1
			assertTrue(file1.setLastModified(file1.lastModified() + 1000));

			assertNotNull(git.commit().setMessage("edit file").setOnly("file1.txt")
					.call());

			cache = db.readDirCache();
			assertEquals(file1Size
			assertEquals(0
		}
	}

	@Test
	public void commitAfterSquashMerge() throws Exception {
		try (Git git = new Git(db)) {
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

			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertEquals(MergeResult.MergeStatus.FAST_FORWARD_SQUASHED
					result.getMergeStatus());

			RevCommit squashedCommit = git.commit().call();

			assertEquals(1
			assertNull(db.readSquashCommitMsg());
			assertEquals("commit: Squashed commit of the following:"
					.getReflogReader(Constants.HEAD).getLastEntry().getComment());
			assertEquals("commit: Squashed commit of the following:"
					.getReflogReader(db.getBranch()).getLastEntry().getComment());
		}
	}

	@Test
	public void testReflogs() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			git.commit().setMessage("c1").call();
			writeTrashFile("f"
			git.commit().setMessage("c2").setAll(true).setReflogComment(null)
					.call();
			writeTrashFile("f"
			git.commit().setMessage("c3").setAll(true)
					.setReflogComment("testRl").call();

			db.getReflogReader(Constants.HEAD).getReverseEntries();

			assertEquals("testRl;commit (initial): c1;"
					db.getReflogReader(Constants.HEAD).getReverseEntries()));
			assertEquals("testRl;commit (initial): c1;"
					db.getReflogReader(db.getBranch()).getReverseEntries()));
		}
	}

	private static String reflogComments(List<ReflogEntry> entries) {
		StringBuilder b = new StringBuilder();
		for (ReflogEntry e : entries) {
			b.append(e.getComment()).append(";");
		}
		return b.toString();
	}

	@Test(expected = WrongRepositoryStateException.class)
	public void commitAmendOnInitialShouldFail() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setAmend(true).setMessage("initial commit").call();
		}
	}

	@Test
	public void commitAmendWithoutAuthorShouldSetOriginalAuthorAndAuthorTime()
			throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();

			final String authorName = "First Author";
			final String authorEmail = "author@example.org";
			final Date authorDate = new Date(1349621117000L);
			PersonIdent firstAuthor = new PersonIdent(authorName
					authorDate
			git.commit().setMessage("initial commit").setAuthor(firstAuthor).call();

			RevCommit amended = git.commit().setAmend(true)
					.setMessage("amend commit").call();

			PersonIdent amendedAuthor = amended.getAuthorIdent();
			assertEquals(authorName
			assertEquals(authorEmail
			assertEquals(authorDate.getTime()
		}
	}

	@Test
	public void commitAmendWithAuthorShouldUseIt() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			git.commit().setMessage("initial commit").call();

			RevCommit amended = git.commit().setAmend(true)
					.setAuthor("New Author"
					.setMessage("amend commit").call();

			PersonIdent amendedAuthor = amended.getAuthorIdent();
			assertEquals("New Author"
			assertEquals("newauthor@example.org"
		}
	}

	@Test
	public void commitEmptyCommits() throws Exception {
		try (Git git = new Git(db)) {

			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit initial = git.commit().setMessage("initial commit")
					.call();

			RevCommit emptyFollowUp = git.commit()
					.setAuthor("New Author"
					.setMessage("no change").call();

			assertNotEquals(initial.getId()
			assertEquals(initial.getTree().getId()
					emptyFollowUp.getTree().getId());

			try {
				git.commit().setAuthor("New Author"
						.setMessage("again no change").setAllowEmpty(false)
						.call();
				fail("Didn't get the expected EmptyCommitException");
			} catch (EmptyCommitException e) {
			}

			git.commit().setAuthor("New Author"
					.setMessage("again no change").setOnly("file1")
					.setAllowEmpty(true).call();
		}
	}

	@Test
	public void commitOnlyShouldCommitUnmergedPathAndNotAffectOthers()
			throws Exception {
		DirCache index = db.lockDirCache();
		DirCacheBuilder builder = index.builder();
		addUnmergedEntry("unmerged1"
		addUnmergedEntry("unmerged2"
		DirCacheEntry other = new DirCacheEntry("other");
		other.setFileMode(FileMode.REGULAR_FILE);
		builder.add(other);
		builder.commit();

		writeTrashFile("unmerged1"
		writeTrashFile("unmerged2"
		writeTrashFile("other"

		assertEquals("[other
				+ "[unmerged1
				+ "[unmerged1
				+ "[unmerged1
				+ "[unmerged2
				+ "[unmerged2
				+ "[unmerged2
				indexState(0));

		try (Git git = new Git(db)) {
			RevCommit commit = git.commit().setOnly("unmerged1")
					.setMessage("Only one file").call();

			assertEquals("[other
					+ "[unmerged2
					+ "[unmerged2
					+ "[unmerged2
					indexState(0));

			try (TreeWalk walk = TreeWalk.forPath(db
				assertEquals(FileMode.REGULAR_FILE
			}
		}
	}

	@Test
	public void commitOnlyShouldHandleIgnored() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("subdir/foo"
			writeTrashFile("subdir/bar"
			writeTrashFile(".gitignore"
			git.add().addFilepattern("subdir").call();
			git.commit().setOnly("subdir").setMessage("first commit").call();
			assertEquals("[subdir/foo
					indexState(CONTENT));
		}
	}

	@Test
	public void commitWithAutoCrlfAndNonNormalizedIndex() throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("core"
			config.save();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial").call();
			assertEquals(
					"[file.txt
					indexState(CONTENT));
			config.setString("core"
			config.save();
			writeTrashFile("file.txt"
			writeTrashFile("file2.txt"
			git.add().addFilepattern("file.txt").addFilepattern("file2.txt")
					.call();
			git.commit().setMessage("Second").call();
			assertEquals(
					"[file.txt
							+ "[file2.txt
					indexState(CONTENT));
			writeTrashFile("file2.txt"
			git.add().addFilepattern("file2.txt").call();
			git.commit().setMessage("Third").call();
			assertEquals(
					"[file.txt
							+ "[file2.txt
					indexState(CONTENT));
		}
	}

	private void testConflictWithAutoCrlf(String baseLf
			throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("core"
			config.save();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial").call();
			git.checkout().setCreateBranch(true).setName("side").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit side = git.commit().setMessage("Side").call();
			git.checkout().setName("master");
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Second").call();
			config.setString("core"
			config.save();
			CherryPickResult pick = git.cherryPick().include(side).call();
			assertEquals("Expected a cherry-pick conflict"
					CherryPickStatus.CONFLICTING
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Second").call();
			assertEquals("[file.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void commitConflictWithAutoCrlfBaseCrLfOursLf() throws Exception {
		testConflictWithAutoCrlf("\r\n"
	}

	@Test
	public void commitConflictWithAutoCrlfBaseLfOursLf() throws Exception {
		testConflictWithAutoCrlf("\n"
	}

	@Test
	public void commitConflictWithAutoCrlfBasCrLfOursCrLf() throws Exception {
		testConflictWithAutoCrlf("\r\n"
	}

	@Test
	public void commitConflictWithAutoCrlfBaseLfOursCrLf() throws Exception {
		testConflictWithAutoCrlf("\n"
	}

	private static void addUnmergedEntry(String file
		DirCacheEntry stage1 = new DirCacheEntry(file
		DirCacheEntry stage2 = new DirCacheEntry(file
		DirCacheEntry stage3 = new DirCacheEntry(file
		stage1.setFileMode(FileMode.REGULAR_FILE);
		stage2.setFileMode(FileMode.REGULAR_FILE);
		stage3.setFileMode(FileMode.REGULAR_FILE);
		builder.add(stage1);
		builder.add(stage2);
		builder.add(stage3);
	}

	@Test
	public void callSignerWithProperSigningKey() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();

			String[] signingKey = new String[1];
			PersonIdent[] signingCommitters = new PersonIdent[1];
			AtomicInteger callCount = new AtomicInteger();
			GpgSigner.setDefault(new GpgSigner() {
				@Override
				public void sign(CommitBuilder commit
						PersonIdent signingCommitter
					signingKey[0] = gpgSigningKey;
					signingCommitters[0] = signingCommitter;
					callCount.incrementAndGet();
				}

				@Override
				public boolean canLocateSigningKey(String gpgSigningKey
						PersonIdent signingCommitter
						CredentialsProvider credentialsProvider)
						throws CanceledException {
					return false;
				}
			});

			git.commit().setCommitter(committer).setSign(Boolean.TRUE)
					.setMessage("initial commit")
					.call();
			assertNull(signingKey[0]);
			assertEquals(1
			assertSame(committer

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();

			String expectedConfigSigningKey = "config-" + System.nanoTime();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("user"
					expectedConfigSigningKey);
			config.save();

			git.commit().setCommitter(committer).setSign(Boolean.TRUE)
					.setMessage("initial commit")
					.call();
			assertEquals(expectedConfigSigningKey
			assertEquals(2
			assertSame(committer

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();

			String expectedSigningKey = "my-" + System.nanoTime();
			git.commit().setCommitter(committer).setSign(Boolean.TRUE)
					.setSigningKey(expectedSigningKey)
					.setMessage("initial commit").call();
			assertEquals(expectedSigningKey
			assertEquals(3
			assertSame(committer
		}
	}

	@Test
	public void callSignerOnlyWhenSigning() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();

			AtomicInteger callCount = new AtomicInteger();
			GpgSigner.setDefault(new GpgSigner() {
				@Override
				public void sign(CommitBuilder commit
						PersonIdent signingCommitter
					callCount.incrementAndGet();
				}

				@Override
				public boolean canLocateSigningKey(String gpgSigningKey
						PersonIdent signingCommitter
						CredentialsProvider credentialsProvider)
						throws CanceledException {
					return false;
				}
			});

			git.commit().setMessage("initial commit").call();
			assertEquals(0

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();

			git.commit().setSign(Boolean.TRUE).setMessage("commit").call();
			assertEquals(1

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();

			StoredConfig config = git.getRepository().getConfig();
			config.setBoolean("commit"
			config.save();

			git.commit().setMessage("commit").call();
			assertEquals(2

			writeTrashFile("file4"
			git.add().addFilepattern("file4").call();

			git.commit().setSign(Boolean.FALSE).setMessage("commit").call();
			assertEquals(2
		}
	}
}
