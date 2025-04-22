
package org.eclipse.jgit.treewalk;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.security.MessageDigest;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.WorkingTreeIterator.MetadataDiff;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Before;
import org.junit.Test;

public class FileTreeIteratorTest extends RepositoryTestCase {
	private final String[] paths = { "a

	private long[] mtime;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		mtime = new long[paths.length];
		for (int i = paths.length - 1; i >= 0; i--) {
			final String s = paths[i];
			writeTrashFile(s
			mtime[i] = FS.DETECTED.lastModified(new File(trash
		}
	}

	@Test
	public void testGetEntryContentLength() throws Exception {
		final FileTreeIterator fti = new FileTreeIterator(db);
		fti.next(1);
		assertEquals(3
		fti.back(1);
		assertEquals(2
		fti.next(1);
		assertEquals(3
		fti.reset();
		assertEquals(2
	}

	@Test
	public void testEmptyIfRootIsFile() throws Exception {
		final File r = new File(trash
		assertTrue(r.isFile());
		final FileTreeIterator fti = new FileTreeIterator(r
				db.getConfig().get(WorkingTreeOptions.KEY));
		assertTrue(fti.first());
		assertTrue(fti.eof());
	}

	@Test
	public void testEmptyIfRootDoesNotExist() throws Exception {
		final File r = new File(trash
		assertFalse(r.exists());
		final FileTreeIterator fti = new FileTreeIterator(r
				db.getConfig().get(WorkingTreeOptions.KEY));
		assertTrue(fti.first());
		assertTrue(fti.eof());
	}

	@Test
	public void testEmptyIfRootIsEmpty() throws Exception {
		final File r = new File(trash
		assertFalse(r.exists());
		FileUtils.mkdir(r);

		final FileTreeIterator fti = new FileTreeIterator(r
				db.getConfig().get(WorkingTreeOptions.KEY));
		assertTrue(fti.first());
		assertTrue(fti.eof());
	}

	@Test
	public void testEmptyIteratorOnEmptyDirectory() throws Exception {
		String nonExistingFileName = "not-existing-file";
		final File r = new File(trash
		assertFalse(r.exists());
		FileUtils.mkdir(r);

		final FileTreeIterator parent = new FileTreeIterator(db);

		while (!parent.getEntryPathString().equals(nonExistingFileName))
			parent.next(1);

		final FileTreeIterator childIter = new FileTreeIterator(parent
				db.getFS());
		assertTrue(childIter.first());
		assertTrue(childIter.eof());

		String parentPath = parent.getEntryPathString();
		assertEquals(nonExistingFileName

		String childPath = childIter.getEntryPathString();

		EmptyTreeIterator e = childIter.createEmptyTreeIterator();
		assertNotNull(e);

		assertEquals(parentPath
		assertEquals(parentPath + "/"
		assertEquals(parentPath + "/"
		assertEquals(childPath + "/"
	}

	@Test
	public void testSimpleIterate() throws Exception {
		final FileTreeIterator top = new FileTreeIterator(trash
				db.getConfig().get(WorkingTreeOptions.KEY));

		assertTrue(top.first());
		assertFalse(top.eof());
		assertEquals(FileMode.REGULAR_FILE.getBits()
		assertEquals(paths[0]
		assertEquals(paths[0].length()
		assertEquals(mtime[0]

		top.next(1);
		assertFalse(top.first());
		assertFalse(top.eof());
		assertEquals(FileMode.REGULAR_FILE.getBits()
		assertEquals(paths[1]
		assertEquals(paths[1].length()
		assertEquals(mtime[1]

		top.next(1);
		assertFalse(top.first());
		assertFalse(top.eof());
		assertEquals(FileMode.TREE.getBits()

		final ObjectReader reader = db.newObjectReader();
		final AbstractTreeIterator sub = top.createSubtreeIterator(reader);
		assertTrue(sub instanceof FileTreeIterator);
		final FileTreeIterator subfti = (FileTreeIterator) sub;
		assertTrue(sub.first());
		assertFalse(sub.eof());
		assertEquals(paths[2]
		assertEquals(paths[2].length()
		assertEquals(mtime[2]

		sub.next(1);
		assertTrue(sub.eof());

		top.next(1);
		assertFalse(top.first());
		assertFalse(top.eof());
		assertEquals(FileMode.REGULAR_FILE.getBits()
		assertEquals(paths[3]
		assertEquals(paths[3].length()
		assertEquals(mtime[3]

		top.next(1);
		assertTrue(top.eof());
	}

	@Test
	public void testComputeFileObjectId() throws Exception {
		final FileTreeIterator top = new FileTreeIterator(trash
				db.getConfig().get(WorkingTreeOptions.KEY));

		final MessageDigest md = Constants.newMessageDigest();
		md.update(Constants.encodeASCII(Constants.TYPE_BLOB));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(paths[0].length()));
		md.update((byte) 0);
		md.update(Constants.encode(paths[0]));
		final ObjectId expect = ObjectId.fromRaw(md.digest());

		assertEquals(expect

		FileUtils.delete(new File(trash
		assertEquals(expect
	}

	@Test
	public void testDirCacheMatchingId() throws Exception {
		File f = writeTrashFile("file"
		try (Git git = new Git(db)) {
			writeTrashFile("file"
			fsTick(f);
			git.add().addFilepattern("file").call();
		}
		DirCacheEntry dce = db.readDirCache().getEntry("file");
		TreeWalk tw = new TreeWalk(db);
		FileTreeIterator fti = new FileTreeIterator(trash
				.getConfig().get(WorkingTreeOptions.KEY));
		tw.addTree(fti);
		DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
		tw.addTree(dci);
		fti.setDirCacheIterator(tw
		while (tw.next() && !tw.getPathString().equals("file")) {
		}
		assertEquals(MetadataDiff.EQUAL
		ObjectId fromRaw = ObjectId.fromRaw(fti.idBuffer()
		assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea"
				fromRaw.getName());
		try (ObjectReader objectReader = db.newObjectReader()) {
			assertFalse(fti.isModified(dce
		}
	}

	@Test
	public void testTreewalkEnterSubtree() throws Exception {
		try (Git git = new Git(db); TreeWalk tw = new TreeWalk(db)) {
			writeTrashFile("b/c"
			writeTrashFile("z/.git"
			git.add().addFilepattern(".").call();
			git.rm().addFilepattern("a
					.addFilepattern("a0b").call();
			assertEquals("[a/b
					indexState(0));
			FileUtils.delete(new File(db.getWorkTree()
					FileUtils.RECURSIVE);

			tw.addTree(new DirCacheIterator(db.readDirCache()));
			tw.addTree(new FileTreeIterator(db));
			assertTrue(tw.next());
			assertEquals("a"
			tw.enterSubtree();
			tw.next();
			assertEquals("a/b"
			tw.next();
			assertEquals("b"
			tw.enterSubtree();
			tw.next();
			assertEquals("b/c"
			assertNotNull(tw.getTree(0
			assertNotNull(tw.getTree(EmptyTreeIterator.class));
		}
	}

	@Test
	public void testIsModifiedSymlinkAsFile() throws Exception {
		writeTrashFile("symlink"
		try (Git git = new Git(db)) {
			db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
			git.add().addFilepattern("symlink").call();
			git.commit().setMessage("commit").call();
		}

		DirCacheEntry dce = db.readDirCache().getEntry("symlink");
		dce.setFileMode(FileMode.SYMLINK);
		try (ObjectReader objectReader = db.newObjectReader()) {
			DirCacheCheckout.checkoutEntry(db

			FileTreeIterator fti = new FileTreeIterator(trash
					db.getConfig().get(WorkingTreeOptions.KEY));
			while (!fti.getEntryPathString().equals("symlink"))
				fti.next(1);
			assertFalse(fti.isModified(dce
		}
	}

	@Test
	public void testIsModifiedFileSmudged() throws Exception {
		File f = writeTrashFile("file"
		try (Git git = new Git(db)) {
			fsTick(f);
			writeTrashFile("file"
			long lastModified = f.lastModified();
			git.add().addFilepattern("file").call();
			writeTrashFile("file"
			f.setLastModified(lastModified);
			db.getIndexFile().setLastModified(lastModified);
		}
		DirCacheEntry dce = db.readDirCache().getEntry("file");
		FileTreeIterator fti = new FileTreeIterator(trash
				.getConfig().get(WorkingTreeOptions.KEY));
		while (!fti.getEntryPathString().equals("file"))
			fti.next(1);
		assertEquals(MetadataDiff.SMUDGED
		try (ObjectReader objectReader = db.newObjectReader()) {
			assertTrue(fti.isModified(dce
		}
	}

	@Test
	public void submoduleHeadMatchesIndex() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			final RevCommit id = git.commit().setMessage("create file").call();
			final String path = "sub";
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(path) {

				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.GITLINK);
					ent.setObjectId(id);
				}
			});
			editor.commit();

			Git.cloneRepository().setURI(db.getDirectory().toURI().toString())
					.setDirectory(new File(db.getWorkTree()
					.getRepository().close();

			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db);
			walk.addTree(indexIter);
			walk.addTree(workTreeIter);
			walk.setFilter(PathFilter.create(path));

			assertTrue(walk.next());
			assertTrue(indexIter.idEqual(workTreeIter));
		}
	}

	@Test
	public void submoduleWithNoGitDirectory() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			final RevCommit id = git.commit().setMessage("create file").call();
			final String path = "sub";
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(path) {

				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.GITLINK);
					ent.setObjectId(id);
				}
			});
			editor.commit();

			File submoduleRoot = new File(db.getWorkTree()
			assertTrue(submoduleRoot.mkdir());
			assertTrue(new File(submoduleRoot

			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db);
			walk.addTree(indexIter);
			walk.addTree(workTreeIter);
			walk.setFilter(PathFilter.create(path));

			assertTrue(walk.next());
			assertFalse(indexIter.idEqual(workTreeIter));
			assertEquals(ObjectId.zeroId()
		}
	}

	@Test
	public void submoduleWithNoHead() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			final RevCommit id = git.commit().setMessage("create file").call();
			final String path = "sub";
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(path) {

				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.GITLINK);
					ent.setObjectId(id);
				}
			});
			editor.commit();

			assertNotNull(Git.init().setDirectory(new File(db.getWorkTree()
					.call().getRepository());

			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db);
			walk.addTree(indexIter);
			walk.addTree(workTreeIter);
			walk.setFilter(PathFilter.create(path));

			assertTrue(walk.next());
			assertFalse(indexIter.idEqual(workTreeIter));
			assertEquals(ObjectId.zeroId()
		}
	}

	@Test
	public void submoduleDirectoryIterator() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			final RevCommit id = git.commit().setMessage("create file").call();
			final String path = "sub";
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(path) {

				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.GITLINK);
					ent.setObjectId(id);
				}
			});
			editor.commit();

			Git.cloneRepository().setURI(db.getDirectory().toURI().toString())
					.setDirectory(new File(db.getWorkTree()
					.getRepository().close();

			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db.getWorkTree()
					db.getFS()
			walk.addTree(indexIter);
			walk.addTree(workTreeIter);
			walk.setFilter(PathFilter.create(path));

			assertTrue(walk.next());
			assertTrue(indexIter.idEqual(workTreeIter));
		}
	}

	@Test
	public void submoduleNestedWithHeadMatchingIndex() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			final RevCommit id = git.commit().setMessage("create file").call();
			final String path = "sub/dir1/dir2";
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(path) {

				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.GITLINK);
					ent.setObjectId(id);
				}
			});
			editor.commit();

			Git.cloneRepository().setURI(db.getDirectory().toURI().toString())
					.setDirectory(new File(db.getWorkTree()
					.getRepository().close();

			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db);
			walk.addTree(indexIter);
			walk.addTree(workTreeIter);
			walk.setFilter(PathFilter.create(path));

			assertTrue(walk.next());
			assertTrue(indexIter.idEqual(workTreeIter));
		}
	}

	@Test
	public void idOffset() throws Exception {
		try (Git git = new Git(db);
				TreeWalk tw = new TreeWalk(db)) {
			writeTrashFile("fileAinfsonly"
			File fileBinindex = writeTrashFile("fileBinindex"
			fsTick(fileBinindex);
			git.add().addFilepattern("fileBinindex").call();
			writeTrashFile("fileCinfsonly"
			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db);
			tw.addTree(indexIter);
			tw.addTree(workTreeIter);
			workTreeIter.setDirCacheIterator(tw
			assertEntry("d46c305e85b630558ee19cc47e73d2e5c8c64cdc"
			assertEntry("0000000000000000000000000000000000000000"
			assertEntry("b8d30ff397626f0f1d3538d66067edf865e201d6"
			assertEntry("8c7e5a667f1b771847fe88c01c3de34413a1b220"
					"fileAinfsonly"
			assertEntry("7371f47a6f8bd23a8fa1a8b2a9479cdd76380e54"
					tw);
			assertEntry("96d80cd6c4e7158dbebd0849f4fb7ce513e5828c"
					"fileCinfsonly"
			assertFalse(tw.next());
		}
	}

	private final FileTreeIterator.FileModeStrategy NO_GITLINKS_STRATEGY =
			new FileTreeIterator.FileModeStrategy() {
				@Override
				public FileMode getMode(File f
					if (attributes.isSymbolicLink()) {
						return FileMode.SYMLINK;
					} else if (attributes.isDirectory()) {
						return FileMode.TREE;
					} else if (attributes.isExecutable()) {
						return FileMode.EXECUTABLE_FILE;
					} else {
						return FileMode.REGULAR_FILE;
					}
				}
			};

	private Repository createNestedRepo() throws IOException {
		File gitdir = createUniqueTestGitDir(false);
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setGitDir(gitdir);
		Repository nestedRepo = builder.build();
		nestedRepo.create();

		JGitTestUtil.writeTrashFile(nestedRepo

		File nestedRepoPath = new File(nestedRepo.getWorkTree()
		FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
		nestedBuilder.setWorkTree(nestedRepoPath);
		nestedBuilder.build().create();

		JGitTestUtil.writeTrashFile(nestedRepo
				"content b");

		return nestedRepo;
	}

	@Test
	public void testCustomFileModeStrategy() throws Exception {
		Repository nestedRepo = createNestedRepo();

		try (Git git = new Git(nestedRepo)) {
			WorkingTreeIterator customIterator = new FileTreeIterator(
					nestedRepo
			git.add().setWorkingTreeIterator(customIterator).addFilepattern(".")
					.call();
			assertEquals(
					"[sub/a.txt
							+ "[sub/nested/b.txt
					indexState(nestedRepo
		}
	}

	@Test
	public void testCustomFileModeStrategyFromParentIterator() throws Exception {
		Repository nestedRepo = createNestedRepo();

		try (Git git = new Git(nestedRepo)) {
			FileTreeIterator customIterator = new FileTreeIterator(nestedRepo
					NO_GITLINKS_STRATEGY);
			File r = new File(nestedRepo.getWorkTree()

			FileTreeIterator childIterator = new FileTreeIterator(
					customIterator
			git.add().setWorkingTreeIterator(childIterator).addFilepattern(".")
					.call();
			assertEquals(
					"[sub/a.txt
							+ "[sub/nested/b.txt
					indexState(nestedRepo
		}
	}

	@Test
	public void testFileModeSymLinkIsNotATree() throws IOException {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = db.getFS();
		writeTrashFile("mÃ¥l/data"
		File file = new File(trash

		try {
			file.toPath();
		} catch (InvalidPathException e) {
			assumeNoException(e);
		}

		fs.createSymLink(file
		FileTreeIterator fti = new FileTreeIterator(db);
		assertFalse(fti.eof());
		while (!fti.getEntryPathString().equals("lÃ¤nk")) {
			fti.next(1);
		}
		assertEquals("lÃ¤nk"
		assertEquals(FileMode.SYMLINK
		fti.next(1);
		assertFalse(fti.eof());
		assertEquals("mÃ¥l"
		assertEquals(FileMode.TREE
		fti.next(1);
		assertTrue(fti.eof());
	}

	@Test
	public void testSymlinkNotModifiedThoughNormalized() throws Exception {
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		final String UNNORMALIZED = "target/";
		final byte[] UNNORMALIZED_BYTES = Constants.encode(UNNORMALIZED);
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					UNNORMALIZED_BYTES
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(UNNORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
		try (Git git = new Git(db)) {
			git.commit().setMessage("Adding link").call();
			git.reset().setMode(ResetType.HARD).call();
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			FileTreeIterator fti = new FileTreeIterator(db);

			while (!fti.getEntryPathString().equals("link")) {
				fti.next(1);
			}
			assertEquals("link"
			assertEquals("link"

			assertFalse(fti.isModified(dci.getDirCacheEntry()
					db.newObjectReader()));
		}
	}

	@Test
	public void testSymlinkModifiedNotNormalized() throws Exception {
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		final String NORMALIZED = "target";
		final byte[] NORMALIZED_BYTES = Constants.encode(NORMALIZED);
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					NORMALIZED_BYTES
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(NORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
		try (Git git = new Git(db)) {
			git.commit().setMessage("Adding link").call();
			git.reset().setMode(ResetType.HARD).call();
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			FileTreeIterator fti = new FileTreeIterator(db);

			while (!fti.getEntryPathString().equals("link")) {
				fti.next(1);
			}
			assertEquals("link"
			assertEquals("link"

			assertFalse(fti.isModified(dci.getDirCacheEntry()
					db.newObjectReader()));
		}
	}

	@Test
	public void testSymlinkActuallyModified() throws Exception {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		final String NORMALIZED = "target";
		final byte[] NORMALIZED_BYTES = Constants.encode(NORMALIZED);
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					NORMALIZED_BYTES
			DirCache dc = db.lockDirCache();
			DirCacheEditor dce = dc.editor();
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(NORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
		try (Git git = new Git(db)) {
			git.commit().setMessage("Adding link").call();
			git.reset().setMode(ResetType.HARD).call();

			FileUtils.delete(new File(trash
			FS.DETECTED.createSymLink(new File(trash
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			FileTreeIterator fti = new FileTreeIterator(db);

			while (!fti.getEntryPathString().equals("link")) {
				fti.next(1);
			}
			assertEquals("link"
			assertEquals("link"

			assertTrue(fti.isModified(dci.getDirCacheEntry()
					db.newObjectReader()));
		}
	}

	private static void assertEntry(String sha1string
			throws MissingObjectException
			CorruptObjectException
		assertTrue(tw.next());
		assertEquals(path
		assertEquals(sha1string
	}

	private static String nameOf(AbstractTreeIterator i) {
		return RawParseUtils.decode(UTF_8
	}
}
