package org.eclipse.jgit.symlinks;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator.FileEntry;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class SymlinksTest extends RepositoryTestCase {
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
	}

	@Test
	public void fileModeTestFileThenSymlink() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add files a & b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add symlink a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.SYMLINK

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.REGULAR_FILE
		}
	}

	@Test
	public void fileModeTestSymlinkThenFile() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("b"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b & symlink a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.SYMLINK
		}
	}

	@Test
	public void fileModeTestFolderThenSymlink() throws Exception {
		try (Git git = new Git(db)) {
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/b"
			writeTrashFile("c"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add folder a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add symlink a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.SYMLINK

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.TREE
		}
	}

	@Test
	public void fileModeTestSymlinkThenFolder() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("c"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add symlink a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/b"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add folder a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.TREE

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.SYMLINK
		}
	}

	@Test
	public void fileModeTestMissingThenSymlink() throws Exception {
		try (Git git = new Git(db);
				TreeWalk tw = new TreeWalk(db);) {
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			RevCommit commit1 = git.commit().setMessage("add file b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("a").call();
			RevCommit commit2 = git.commit().setMessage("add symlink a").call();

			git.checkout().setName(branch_1.getName()).call();

			tw.addTree(commit1.getTree());
			tw.addTree(commit2.getTree());
			List<DiffEntry> scan = DiffEntry.scan(tw);
			assertEquals(1
			assertEquals(FileMode.SYMLINK
			assertEquals(FileMode.MISSING
		}
	}

	@Test
	public void fileModeTestSymlinkThenMissing() throws Exception {
		try (Git git = new Git(db);
				TreeWalk tw = new TreeWalk(db);) {
			writeTrashFile("b"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern(".").call();
			RevCommit commit1 = git.commit().setMessage("add file b & symlink a")
					.call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			RevCommit commit2 = git.commit().setMessage("delete symlink a").call();

			git.checkout().setName(branch_1.getName()).call();

			tw.addTree(commit1.getTree());
			tw.addTree(commit2.getTree());
			List<DiffEntry> scan = DiffEntry.scan(tw);
			assertEquals(1
			assertEquals(FileMode.MISSING
			assertEquals(FileMode.SYMLINK
		}
	}

	@Test
	public void createSymlinkAfterTarget() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit base = git.commit().setMessage("init").call();
			writeTrashFile("target"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("target").addFilepattern("link").call();
			git.commit().setMessage("add target").call();
			assertEquals(4
			git.checkout().setName(base.name()).call();
			assertEquals(2
			git.checkout().setName("master").call();
			assertEquals(4
			String data = read(new File(db.getWorkTree()
			assertEquals(8
			assertEquals("someData"
			data = read(new File(db.getWorkTree()
			assertEquals("target"
					FileUtils.readSymLink(new File(db.getWorkTree()
			assertEquals("someData"
		}
	}

	@Test
	public void createFileSymlinkBeforeTarget() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit base = git.commit().setMessage("init").call();
			writeTrashFile("target"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("target").addFilepattern("tlink").call();
			git.commit().setMessage("add target").call();
			assertEquals(4
			git.checkout().setName(base.name()).call();
			assertEquals(2
			git.checkout().setName("master").call();
			assertEquals(4
			String data = read(new File(db.getWorkTree()
			assertEquals(8
			assertEquals("someData"
			data = read(new File(db.getWorkTree()
			assertEquals("target"
					FileUtils.readSymLink(new File(db.getWorkTree()
			assertEquals("someData"
		}
	}

	@Test
	public void createDirSymlinkBeforeTarget() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit base = git.commit().setMessage("init").call();
			FileUtils.createSymLink(new File(db.getWorkTree()
			FileUtils.mkdir(new File(db.getWorkTree()
			writeTrashFile("target/file"
			git.add().addFilepattern("target").addFilepattern("link").call();
			git.commit().setMessage("add target").call();
			assertEquals(4
			git.checkout().setName(base.name()).call();
			assertEquals(2
			git.checkout().setName("master").call();
			assertEquals(4
			String data = read(new File(db.getWorkTree()
			assertEquals(8
			assertEquals("someData"
			data = read(new File(db.getWorkTree()
			assertEquals("target"
					FileUtils.readSymLink(new File(db.getWorkTree()
			assertEquals("someData"
		}
	}
}
