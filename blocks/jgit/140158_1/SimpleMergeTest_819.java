package org.eclipse.jgit.merge;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoMergeBaseException;
import org.eclipse.jgit.errors.NoMergeBaseException.MergeBaseFailureReason;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MergerTest extends RepositoryTestCase {

	@DataPoints
	public static MergeStrategy[] strategiesUnderTest = new MergeStrategy[] {
			MergeStrategy.RECURSIVE

	@Theory
	public void failingDeleteOfDirectoryWithUntrackedContent(
			MergeStrategy strategy) throws Exception {
		File folder1 = new File(db.getWorkTree()
		FileUtils.mkdir(folder1);
		File file = new File(folder1
		write(file
		file = new File(folder1
		write(file

		try (Git git = new Git(db)) {
			git.add().addFilepattern(folder1.getName()).call();
			RevCommit base = git.commit().setMessage("adding folder").call();

			recursiveDelete(folder1);
			git.rm().addFilepattern("folder1/file1.txt")
					.addFilepattern("folder1/file2.txt").call();
			RevCommit other = git.commit()
					.setMessage("removing folders on 'other'").call();

			git.checkout().setName(base.name()).call();

			file = new File(db.getWorkTree()
			write(file

			git.add().addFilepattern("unrelated.txt").call();
			RevCommit head = git.commit().setMessage("Adding another file").call();

			file = new File(folder1
			write(file

			ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
			merger.setCommitNames(new String[] { "BASE"
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
			boolean ok = merger.merge(head.getId()
			assertTrue(ok);
			assertTrue(file.exists());
		}
	}

	@Theory
	public void checkMergeConflictingTreesWithoutIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.commit().setAll(true).setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.CONFLICTING
		assertEquals(
				"[d/1
				indexState(CONTENT));
	}

	@Theory
	public void checkMergeMergeableTreesWithoutIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.commit().setAll(true).setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals("[d/1
				indexState(CONTENT));
	}

	@Theory
	public void checkUntrackedFolderIsNotAConflict(
			MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("e/1"
		git.add().addFilepattern("e/1").call();
		RevCommit masterCommit = git.commit().setMessage("added e/1").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("f/1"
		git.add().addFilepattern("f/1").call();
		git.commit().setAll(true).setMessage("added f/1")
				.call();

		writeTrashFile("e/2"

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[d/1
				indexState(CONTENT));
	}

	@Theory
	public void checkFileReplacedByFolderInTheirs(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("sub"
		git.add().addFilepattern("sub").call();
		RevCommit first = git.commit().setMessage("initial").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();

		git.rm().addFilepattern("sub").call();
		writeTrashFile("sub/file"
		git.add().addFilepattern("sub/file").call();
		RevCommit masterCommit = git.commit().setMessage("file -> folder")
				.call();

		git.checkout().setName("master").call();
		writeTrashFile("noop"
		git.add().addFilepattern("noop").call();
		git.commit().setAll(true).setMessage("noop").call();

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[noop
				indexState(CONTENT));
	}

	@Theory
	public void checkFileReplacedByFolderInOurs(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("sub"
		git.add().addFilepattern("sub").call();
		RevCommit first = git.commit().setMessage("initial").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("noop"
		git.add().addFilepattern("noop").call();
		RevCommit sideCommit = git.commit().setAll(true).setMessage("noop")
				.call();

		git.checkout().setName("master").call();
		git.rm().addFilepattern("sub").call();
		writeTrashFile("sub/file"
		git.add().addFilepattern("sub/file").call();
		git.commit().setMessage("file -> folder")
				.call();

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(sideCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[noop
				indexState(CONTENT));
	}

	@Theory
	public void checkUntrackedEmpytFolderIsNotAConflictWithFile(
			MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("e"
		git.add().addFilepattern("e").call();
		RevCommit masterCommit = git.commit().setMessage("added e").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("f/1"
		git.add().addFilepattern("f/1").call();
		git.commit().setAll(true).setMessage("added f/1").call();

		FileUtils.mkdirs(new File(trash

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[d/1
				indexState(CONTENT));
	}

	@Theory
	public void mergeWithCrlfInWT(MergeStrategy strategy) throws IOException
			GitAPIException {
		Git git = Git.wrap(db);
		db.getConfig().setString("core"
		db.getConfig().save();
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("base").call();

		git.branchCreate().setName("brancha").call();

		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on master").call();

		git.checkout().setName("brancha").call();
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		db.getConfig().setString("core"
		db.getConfig().save();

		MergeResult mergeResult = git.merge().setStrategy(strategy)
				.include(db.resolve("master"))
				.call();
		assertEquals(MergeResult.MergeStatus.MERGED
				mergeResult.getMergeStatus());
	}

	@Theory
	public void mergeWithCrlfAutoCrlfTrue(MergeStrategy strategy)
			throws IOException
		Git git = Git.wrap(db);
		db.getConfig().setString("core"
		db.getConfig().save();
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("base").call();

		git.branchCreate().setName("brancha").call();

		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on master").call();

		git.checkout().setName("brancha").call();
		File testFile = writeTrashFile("crlf.txt"
				"a first line\r\na crlf file\r\n");
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		MergeResult mergeResult = git.merge().setStrategy(strategy)
				.include(db.resolve("master")).call();
		assertEquals(MergeResult.MergeStatus.MERGED
				mergeResult.getMergeStatus());
		checkFile(testFile
		assertEquals(
				"[crlf.txt
				indexState(CONTENT));
	}

	@Theory
	public void rebaseWithCrlfAutoCrlfTrue(MergeStrategy strategy)
			throws IOException
		Git git = Git.wrap(db);
		db.getConfig().setString("core"
		db.getConfig().save();
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		RevCommit first = git.commit().setMessage("base").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("brancha").call();

		File testFile = writeTrashFile("crlf.txt"
				"line 1\r\nmodified line\r\nline 3\r\n");
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		git.checkout().setName("master").call();
		File otherFile = writeTrashFile("otherfile.txt"
		git.add().addFilepattern("otherfile.txt").call();
		git.commit().setMessage("on master").call();

		git.checkout().setName("brancha").call();
		checkFile(testFile
		assertFalse(otherFile.exists());

		RebaseResult rebaseResult = git.rebase().setStrategy(strategy)
				.setUpstream(db.resolve("master")).call();
		assertEquals(RebaseResult.Status.OK
		checkFile(testFile
		checkFile(otherFile
		assertEquals(
				"[crlf.txt
						+ "[otherfile.txt
				indexState(CONTENT));
	}

	@Theory
	public void checkMergeEqualTreesWithoutIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.commit().setAll(true).setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals("[d/1
				indexState(CONTENT));
	}

	@Theory
	public void checkMergeEqualTreesInCore(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();

		ThreeWayMerger resolveMerger = (ThreeWayMerger) strategy.newMerger(db
				true);
		boolean noProblems = resolveMerger.merge(masterCommit
		assertTrue(noProblems);
	}

	@Theory
	public void checkMergeEqualTreesInCore_noRepo(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ThreeWayMerger resolveMerger =
					(ThreeWayMerger) strategy.newMerger(ins
			boolean noProblems = resolveMerger.merge(masterCommit
			assertTrue(noProblems);
		}
	}

	@Theory
	public void checkMergeEqualNewTrees(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("2"
		git.add().addFilepattern("2").call();
		RevCommit first = git.commit().setMessage("added 2").call();

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("added d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		git.commit().setAll(true).setMessage("added d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[2
				indexState(CONTENT));
	}

	@Theory
	public void checkMergeConflictingNewTrees(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("2"
		git.add().addFilepattern("2").call();
		RevCommit first = git.commit().setMessage("added 2").call();

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("added d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		git.commit().setAll(true).setMessage("added d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.CONFLICTING
		assertEquals(
				"[2
				indexState(CONTENT));
	}

	@Theory
	public void checkMergeConflictingFilesWithTreeInIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("0"
		git.add().addFilepattern("0").call();
		RevCommit first = git.commit().setMessage("added 0").call();

		writeTrashFile("0"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified 0 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("0"
		git.commit().setAll(true).setMessage("modified 0 on side").call();

		git.rm().addFilepattern("0").call();
		writeTrashFile("0/0"
		git.add().addFilepattern("0/0").call();
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.FAILED
	}

	@Theory
	public void checkMergeMergeableFilesWithTreeInIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("0"
		writeTrashFile("1"
		git.add().addFilepattern("0").addFilepattern("1").call();
		RevCommit first = git.commit().setMessage("added 0

		writeTrashFile("1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("1"
		git.commit().setAll(true).setMessage("modified 1 on side").call();

		git.rm().addFilepattern("0").call();
		writeTrashFile("0/0"
		git.add().addFilepattern("0/0").call();
		try {
			git.merge().setStrategy(strategy).include(masterCommit).call();
			Assert.fail("Didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertEquals(1
			assertEquals("0/0"
		}
	}

	@Theory
	public void checkContentMergeNoConflict(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		git.commit().setAll(true).setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		git.checkout().setName("master").call();
		MergeResult result =
				git.merge().setStrategy(strategy).include(sideCommit).call();
		assertEquals(MergeStatus.MERGED
		String expected = "1master\n2\n3side";
		assertEquals(expected
	}

	@Theory
	public void checkContentMergeNoConflict_noRepo(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ResolveMerger merger =
					(ResolveMerger) strategy.newMerger(ins
			boolean noProblems = merger.merge(masterCommit
			assertTrue(noProblems);
			assertEquals("1master\n2\n3side"
					readBlob(merger.getResultTreeId()
		}
	}


	@Theory
	public void checkContentMergeLargeBinaries(MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);
		final int LINELEN = 72;

		byte[] binary = new byte[LINELEN * 2000];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte)((i % LINELEN) == 0 ? '\n' : 'x');
		}
		binary[50] = '\0';

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		int idx = LINELEN * 1200 + 1;
		byte save = binary[idx];
		binary[idx] = '@';
		writeTrashFile("file"

		binary[idx] = save;
		git.add().addFilepattern("file").call();
		RevCommit masterCommit = git.commit().setAll(true)
			.setMessage("modified file l 1200").call();

		git.checkout().setCreateBranch(true).setStartPoint(first).setName("side").call();
		binary[LINELEN * 1500 + 1] = '!';
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit sideCommit = git.commit().setAll(true)
			.setMessage("modified file l 1500").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ObjectInserter forbidInserter = new ObjectInserter.Filter() {
				@Override
				protected ObjectInserter delegate() {
					return ins;
				}

				@Override
				public ObjectReader newReader() {
					return new BigReadForbiddenReader(super.newReader()
				}
			};

			ResolveMerger merger =
				(ResolveMerger) strategy.newMerger(forbidInserter
			boolean noProblems = merger.merge(masterCommit
			assertFalse(noProblems);
		}
	}

	static class BigReadForbiddenStream extends ObjectStream.Filter {
		int limit;

		BigReadForbiddenStream(ObjectStream orig
			super(orig.getType()
			this.limit = limit;
		}

		@Override
		public long skip(long n) throws IOException {
			limit -= n;
			if (limit < 0) {
				throw new IllegalStateException();
			}

			return super.skip(n);
		}

		@Override
		public int read() throws IOException {
			int r = super.read();
			limit--;
			if (limit < 0) {
				throw new IllegalStateException();
			}
			return r;
		}

		@Override
		public int read(byte[] b
			int n = super.read(b
			limit -= n;
			if (limit < 0) {
				throw new IllegalStateException();
			}
			return n;
		}
	}

	static class BigReadForbiddenReader extends ObjectReader.Filter {
		ObjectReader delegate;
		int limit;

		@Override
		protected ObjectReader delegate() {
			return delegate;
		}

		BigReadForbiddenReader(ObjectReader delegate
			this.delegate = delegate;
			this.limit = limit;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
			ObjectLoader orig = super.open(objectId
			return new ObjectLoader.Filter() {
				@Override
				protected ObjectLoader delegate() {
					return orig;
				}

				@Override
				public ObjectStream openStream() throws IOException {
					ObjectStream os = orig.openStream();
					return new BigReadForbiddenStream(os
				}
			};
		}
	}

	@Theory
	public void checkContentMergeConflict(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		git.commit().setAll(true).setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		git.checkout().setName("master").call();
		MergeResult result =
				git.merge().setStrategy(strategy).include(sideCommit).call();
		assertEquals(MergeStatus.CONFLICTING
		String expected = "<<<<<<< HEAD\n"
				+ "1master\n"
				+ "=======\n"
				+ "1side\n"
				+ ">>>>>>> " + sideCommit.name() + "\n"
				+ "2\n"
				+ "3";
		assertEquals(expected
	}

	@Theory
	public void checkContentMergeConflict_noTree(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ResolveMerger merger =
					(ResolveMerger) strategy.newMerger(ins
			boolean noProblems = merger.merge(masterCommit
			assertFalse(noProblems);
			assertEquals(Arrays.asList("file")

			MergeFormatter fmt = new MergeFormatter();
			merger.getMergeResults().get("file");
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				fmt.formatMerge(out
						"BASE"
				String expected = "<<<<<<< OURS\n"
						+ "1master\n"
						+ "=======\n"
						+ "1side\n"
						+ ">>>>>>> THEIRS\n"
						+ "2\n"
						+ "3";
				assertEquals(expected
			}
		}
	}

	@Theory
	public void checkMergeCrissCross(MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("1"
		git.add().addFilepattern("1").call();
		RevCommit first = git.commit().setMessage("added 1").call();

		writeTrashFile("1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified 1 on master").call();

		writeTrashFile("1"
		git.commit().setAll(true)
				.setMessage("modified 1 on master again").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("1"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified 1 on side").call();

		writeTrashFile("1"
		git.commit().setAll(true)
				.setMessage("modified 1 on side again").call();

		MergeResult result = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		result.getNewHead();
		git.checkout().setName("master").call();
		result = git.merge().setStrategy(strategy).include(sideCommit).call();
		assertEquals(MergeStatus.MERGED

		try {
			MergeResult mergeResult = git.merge().setStrategy(strategy)
					.include(git.getRepository().exactRef("refs/heads/side"))
					.call();
			assertEquals(MergeStrategy.RECURSIVE
			assertEquals(MergeResult.MergeStatus.MERGED
					mergeResult.getMergeStatus());
			assertEquals("1master2\n2\n3side2"
		} catch (JGitInternalException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertTrue(e.getCause() instanceof NoMergeBaseException);
			assertEquals(((NoMergeBaseException) e.getCause()).getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
		}
	}

	@Theory
	public void checkLockedFilesToBeDeleted(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		RevCommit first = git.commit().setMessage("added a.txt

		writeTrashFile("a.txt"
		git.rm().addFilepattern("b.txt").call();
		RevCommit masterCommit = git.commit()
				.setMessage("modified a.txt
				.call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("c.txt"
		git.add().addFilepattern("c.txt").call();
		git.commit().setMessage("added c.txt").call();

		try (FileInputStream fis = new FileInputStream(
				new File(db.getWorkTree()
			MergeResult mergeRes = git.merge().setStrategy(strategy)
					.include(masterCommit).call();
			if (mergeRes.getMergeStatus().equals(MergeStatus.FAILED)) {
				assertEquals(1
				assertEquals(MergeFailureReason.COULD_NOT_DELETE
						mergeRes.getFailingPaths().get("b.txt"));
			}
			assertEquals(
					"[a.txt
							+ "[c.txt
					indexState(CONTENT));
		}
	}

	@Theory
	public void checkForCorrectIndex(MergeStrategy strategy) throws Exception {
		File f;
		long lastTs4
		Git git = Git.wrap(db);
		File indexFile = db.getIndexFile();

		f = writeTrashFiles(false
		lastTs4 = FS.DETECTED.lastModified(f);

		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setMessage("initial commit")
				.call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
		assertEquals("Commit should not touch working tree file 4"
				FS.DETECTED.lastModified(new File(db.getWorkTree()
		lastTsIndex = FS.DETECTED.lastModified(indexFile);

		fsTick(indexFile);
		f = writeTrashFiles(false
				null);
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit masterCommit = git.commit().setMessage("master commit")
				.call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
				+ lastTsIndex
		lastTsIndex = FS.DETECTED.lastModified(indexFile);

		fsTick(indexFile);
		git.checkout().setCreateBranch(true).setStartPoint(firstCommit)
				.setName("side").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
				+ lastTsIndex
		lastTsIndex = FS.DETECTED.lastModified(indexFile);

		assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(CONTENT));
		fsTick(indexFile);
		f = writeTrashFiles(false
		lastTs4 = FS.DETECTED.lastModified(f);
		fsTick(f);
		git.add().addFilepattern(".").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("*" + lastTsIndex
				"4"
		lastTsIndex = FS.DETECTED.lastModified(indexFile);

		fsTick(indexFile);
		f = writeTrashFiles(false
		fsTick(f);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("side commit").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("0"
				+ lastTsIndex
		lastTsIndex = FS.DETECTED.lastModified(indexFile);

		fsTick(indexFile);
		git.merge().setStrategy(strategy).include(masterCommit).call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("4"
				+ lastTsIndex
		assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(CONTENT));
	}

	@Theory
	public void checkMergeConflictingSubmodulesWithoutIndex(
			MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("initial"
		git.add().addFilepattern("initial").call();
		RevCommit initial = git.commit().setMessage("initial").call();

		writeSubmodule("one"
				.fromString("1000000000000000000000000000000000000000"));
		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		RevCommit right = git.commit().setMessage("added one").call();


		git.checkout().setStartPoint(initial).setName("left")
				.setCreateBranch(true).call();
		writeSubmodule("one"
				.fromString("2000000000000000000000000000000000000000"));

		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("a different one").call();

		MergeResult result = git.merge().setStrategy(strategy).include(right)
				.call();

		assertEquals(MergeStatus.CONFLICTING
		Map<String
		assertEquals(1
		assertNotNull(conflicts.get("one"));
	}

	@Theory
	public void checkMergeNonConflictingSubmodulesWithoutIndex(
			MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("initial"
		git.add().addFilepattern("initial").call();

		writeSubmodule("one"
				.fromString("1000000000000000000000000000000000000000"));

		String existing = read(Constants.DOT_GIT_MODULES);
		String context = "\n# context\n# more context\n# yet more context\n";
		write(new File(db.getWorkTree()
				existing + context + context + context);

		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		RevCommit initial = git.commit().setMessage("initial").call();

		writeSubmodule("two"
				.fromString("1000000000000000000000000000000000000000"));
		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();

		RevCommit right = git.commit().setMessage("added two").call();

		git.checkout().setStartPoint(initial).setName("left")
				.setCreateBranch(true).call();

		addSubmoduleToIndex("three"
				.fromString("1000000000000000000000000000000000000000"));
		new File(db.getWorkTree()

		existing = read(Constants.DOT_GIT_MODULES);
		String three = "[submodule \"three\"]\n\tpath = three\n\turl = "
				+ db.getDirectory().toURI() + "\n";
		write(new File(db.getWorkTree()
				three + existing);

		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("a different one").call();

		MergeResult result = git.merge().setStrategy(strategy).include(right)
				.call();

		assertNull(result.getCheckoutConflicts());
		assertNull(result.getFailingPaths());
		for (String dir : Arrays.asList("one"
			assertTrue(new File(db.getWorkTree()
		}
	}

	private void writeSubmodule(String path
			throws IOException
		addSubmoduleToIndex(path
		new File(db.getWorkTree()

		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
				db.getDirectory().toURI().toString());
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(
				new File(db.getWorkTree()
				db.getFS());
		modulesConfig.load();
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.save();

	}

	private void addSubmoduleToIndex(String path
			throws IOException {
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new DirCacheEditor.PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(commit);
			}
		});
		editor.commit();
	}

	private void checkConsistentLastModified(String... pathes)
			throws IOException {
		DirCache dc = db.readDirCache();
		File workTree = db.getWorkTree();
		for (String path : pathes)
			assertEquals(
					"IndexEntry with path "
							+ path
							+ " has lastmodified with is different from the worktree file"
					FS.DETECTED.lastModified(new File(workTree
							.getLastModified());
	}

	private void checkModificationTimeStampOrder(String... pathes)
			throws IOException {
		long lastMod = Long.MIN_VALUE;
		for (String p : pathes) {
			boolean strong = p.startsWith("<");
			boolean fixed = p.charAt(strong ? 1 : 0) == '*';
			p = p.substring((strong ? 1 : 0) + (fixed ? 1 : 0));
			long curMod = fixed ? Long.valueOf(p).longValue()
					: FS.DETECTED.lastModified(new File(db.getWorkTree()
			if (strong)
				assertTrue("path " + p + " is not younger than predecesssor"
						curMod > lastMod);
			else
				assertTrue("path " + p + " is older than predecesssor"
						curMod >= lastMod);
		}
	}

	private String readBlob(ObjectId treeish
		try (TestRepository<?> tr = new TestRepository<>(db)) {
			RevWalk rw = tr.getRevWalk();
			RevTree tree = rw.parseTree(treeish);
			RevObject obj = tr.get(tree
			if (obj == null) {
				return null;
			}
			return new String(
					rw.getObjectReader().open(obj
		}
	}
}
