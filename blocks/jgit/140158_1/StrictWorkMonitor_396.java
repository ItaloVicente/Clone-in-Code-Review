
package org.eclipse.jgit.junit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;

public abstract class RepositoryTestCase extends LocalDiskRepositoryTestCase {
	protected static void copyFile(File src
			throws IOException {
		try (FileInputStream fis = new FileInputStream(src);
				FileOutputStream fos = new FileOutputStream(dst)) {
			final byte[] buf = new byte[4096];
			int r;
			while ((r = fis.read(buf)) > 0) {
				fos.write(buf
			}
		}
	}

	protected File writeTrashFile(String name
			throws IOException {
		return JGitTestUtil.writeTrashFile(db
	}

	protected Path writeLink(String link
			throws Exception {
		return JGitTestUtil.writeLink(db
	}

	protected File writeTrashFile(final String subdir
			final String data)
			throws IOException {
		return JGitTestUtil.writeTrashFile(db
	}

	protected String read(String name) throws IOException {
		return JGitTestUtil.read(db
	}

	protected boolean check(String name) {
		return JGitTestUtil.check(db
	}

	protected void deleteTrashFile(String name) throws IOException {
		JGitTestUtil.deleteTrashFile(db
	}

	protected static void checkFile(File f
			throws IOException {
		try (Reader r = new InputStreamReader(new FileInputStream(f)
				UTF_8)) {
			if (checkData.length() > 0) {
				char[] data = new char[checkData.length()];
				assertEquals(data.length
				assertEquals(checkData
			}
			assertEquals(-1
		}
	}

	protected FileRepository db;

	protected File trash;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		trash = db.getWorkTree();
	}

	public String indexState(int includedOptions)
			throws IllegalStateException
		return indexState(db
	}

	protected void resetIndex(FileTreeIterator treeItr)
			throws FileNotFoundException
		try (ObjectInserter inserter = db.newObjectInserter()) {
			DirCacheBuilder builder = db.lockDirCache().builder();
			DirCacheEntry dce;

			while (!treeItr.eof()) {
				long len = treeItr.getEntryLength();

				dce = new DirCacheEntry(treeItr.getEntryPathString());
				dce.setFileMode(treeItr.getEntryFileMode());
				dce.setLastModified(treeItr.getEntryLastModified());
				dce.setLength((int) len);
				try (FileInputStream in = new FileInputStream(
						treeItr.getEntryFile())) {
					dce.setObjectId(
							inserter.insert(Constants.OBJ_BLOB
				}
				builder.add(dce);
				treeItr.next(1);
			}
			builder.commit();
			inserter.flush();
		}
	}

	public static String lookup(Object l
			Map<Object
		String name = lookupTable.get(l);
		if (name == null) {
			name = nameTemplate.replaceAll("%n"
					Integer.toString(lookupTable.size()));
			lookupTable.put(l
		}
		return name;
	}

	public static String slashify(String str) {
		str = str.replace('\\'
		return str;
	}

	public static long fsTick(File lastFile) throws InterruptedException
			IOException {
		long sleepTime = 64;
		FS fs = FS.DETECTED;
		if (lastFile != null && !fs.exists(lastFile))
			throw new FileNotFoundException(lastFile.getPath());
		File tmp = File.createTempFile("FileTreeIteratorWithTimeControl"
		try {
			long startTime = (lastFile == null) ? fs.lastModified(tmp) : fs
					.lastModified(lastFile);
			long actTime = fs.lastModified(tmp);
			while (actTime <= startTime) {
				Thread.sleep(sleepTime);
				sleepTime *= 2;
				try (FileOutputStream fos = new FileOutputStream(tmp)) {
				}
				actTime = fs.lastModified(tmp);
			}
			return actTime;
		} finally {
			FileUtils.delete(tmp);
		}
	}

	protected void createBranch(ObjectId objectId
			throws IOException {
		RefUpdate updateRef = db.updateRef(branchName);
		updateRef.setNewObjectId(objectId);
		updateRef.update();
	}

	protected void checkoutBranch(String branchName)
			throws IllegalStateException
		try (RevWalk walk = new RevWalk(db)) {
			RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
			RevCommit branch = walk.parseCommit(db.resolve(branchName));
			DirCacheCheckout dco = new DirCacheCheckout(db
					head.getTree().getId()
					branch.getTree().getId());
			dco.setFailOnConflict(true);
			dco.checkout();
		}
		RefUpdate refUpdate = db.updateRef(Constants.HEAD);
		refUpdate.setRefLogMessage("checkout: moving to " + branchName
		refUpdate.link(branchName);
	}

	protected File writeTrashFiles(boolean ensureDistinctTimestamps
			String... contents)
			throws IOException
				File f = null;
				for (int i = 0; i < contents.length; i++)
					if (contents[i] != null) {
						if (ensureDistinctTimestamps && (f != null))
							fsTick(f);
						f = writeTrashFile(Integer.toString(i)
					}
				return f;
			}

	protected RevCommit commitFile(String filename
		try (Git git = new Git(db)) {
			Repository repo = git.getRepository();
			String originalBranch = repo.getFullBranch();
			boolean empty = repo.resolve(Constants.HEAD) == null;
			if (!empty) {
				if (repo.findRef(branch) == null)
					git.branchCreate().setName(branch).call();
				git.checkout().setName(branch).call();
			}

			writeTrashFile(filename
			git.add().addFilepattern(filename).call();
			RevCommit commit = git.commit()
					.setMessage(branch + ": " + filename).call();

			if (originalBranch != null)
				git.checkout().setName(originalBranch).call();
			else if (empty)
				git.branchCreate().setName(branch).setStartPoint(commit).call();

			return commit;
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	protected DirCacheEntry createEntry(String path
		return createEntry(path
	}

	protected DirCacheEntry createEntry(final String path
			final String content) {
		return createEntry(path
	}

	protected DirCacheEntry createEntry(final String path
			final int stage
		final DirCacheEntry entry = new DirCacheEntry(path
		entry.setFileMode(mode);
		try (ObjectInserter.Formatter formatter = new ObjectInserter.Formatter()) {
			entry.setObjectId(formatter.idFor(
					Constants.OBJ_BLOB
		}
		return entry;
	}

	public static void assertEqualsFile(File expected
			throws IOException {
		assertEquals(expected.getCanonicalFile()
	}
}
