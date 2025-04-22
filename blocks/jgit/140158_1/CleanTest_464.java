package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator.FileEntry;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Assume;
import org.junit.Test;

public class CheckoutTest extends CLIRepositoryTestCase {
	private String[] executeExpectingException(String command) {
		try {
			execute(command);
			throw new AssertionError("Expected Die");
		} catch (Exception e) {
			return e.getMessage().split(System.lineSeparator());
		}
	}

	@Test
	public void testCheckoutSelf() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			assertStringArrayEquals("Already on 'master'"
					execute("git checkout master"));
		}
	}

	@Test
	public void testCheckoutBranch() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			git.branchCreate().setName("side").call();

			assertStringArrayEquals("Switched to branch 'side'"
					execute("git checkout side"));
		}
	}

	@Test
	public void testCheckoutNewBranch() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			assertStringArrayEquals("Switched to a new branch 'side'"
					execute("git checkout -b side"));
		}
	}

	@Test
	public void testCheckoutNonExistingBranch() throws Exception {
		assertStringArrayEquals(
				"error: pathspec 'side' did not match any file(s) known to git."
				executeExpectingException("git checkout side"));
	}

	@Test
	public void testCheckoutNewBranchThatAlreadyExists() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			assertStringArrayEquals(
					"fatal: A branch named 'master' already exists."
				executeUnchecked("git checkout -b master"));
		}
	}

	@Test
	public void testCheckoutNewBranchOnBranchToBeBorn() throws Exception {
		assertStringArrayEquals("fatal: You are on a branch yet to be born"
				executeUnchecked("git checkout -b side"));
	}

	@Test
	public void testCheckoutUnresolvedHead() throws Exception {
		assertStringArrayEquals(
				"error: pathspec 'HEAD' did not match any file(s) known to git."
				executeExpectingException("git checkout HEAD"));
	}

	@Test
	public void testCheckoutHead() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			assertStringArrayEquals(""
		}
	}

	@Test
	public void testCheckoutExistingBranchWithConflict() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit file a").call();
			git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/b"
			git.add().addFilepattern("a/b").call();
			git.commit().setMessage("commit folder a").call();
			git.rm().addFilepattern("a").call();
			writeTrashFile("a"
			git.add().addFilepattern(".").call();

			String[] execute = executeExpectingException(
					"git checkout branch_1");
			assertEquals(
					"error: Your local changes to the following files would be overwritten by checkout:"
					execute[0]);
			assertEquals("\ta"
		}
	}

	@Test
	public void testCheckoutWithMissingWorkingTreeFile() throws Exception {
		try (Git git = new Git(db)) {
			File fileA = writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add files a & b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("modify file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			FileUtils.delete(fileA);

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.REGULAR_FILE
			assertEquals("Hello world a"
		}
	}

	@Test
	public void testCheckoutOrphan() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			assertStringArrayEquals("Switched to a new branch 'new_branch'"
					execute("git checkout --orphan new_branch"));
			assertEquals("refs/heads/new_branch"
					db.exactRef("HEAD").getTarget().getName());
			RevCommit commit = git.commit().setMessage("orphan commit").call();
			assertEquals(0
		}
	}

	@Test
	public void fileModeTestMissingThenFolderWithFileInWorkingTree()
			throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			File folderA = new File(db.getWorkTree()
			FileUtils.mkdirs(folderA);
			writeTrashFile("a/c"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add folder a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.TREE

			FileUtils.delete(folderA
			writeTrashFile("a"

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.REGULAR_FILE

			try {
				git.checkout().setName(branch_1.getName()).call();
				fail("Don't get the expected conflict");
			} catch (CheckoutConflictException e) {
				assertEquals("[a]"
				entry = new FileTreeIterator.FileEntry(
						new File(db.getWorkTree()
				assertEquals(FileMode.REGULAR_FILE
			}
		}
	}

	@Test
	public void fileModeTestFolderWithMissingInWorkingTree() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("b"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b & file a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			File folderA = new File(db.getWorkTree()
			FileUtils.mkdirs(folderA);
			writeTrashFile("a/c"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add folder a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.TREE

			FileUtils.delete(folderA

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.REGULAR_FILE
		}
	}

	@Test
	public void fileModeTestMissingWithFolderInWorkingTree() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("b"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b & file a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			git.commit().setMessage("delete file a").call();

			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.TREE

			CheckoutConflictException exception = null;
			try {
				git.checkout().setName(branch_1.getName()).call();
			} catch (CheckoutConflictException e) {
				exception = e;
			}
			assertNotNull(exception);
			assertEquals(2
			assertEquals("a"
			assertEquals("a/c"
		}
	}

	@Test
	public void fileModeTestFolderThenMissingWithFileInWorkingTree()
			throws Exception {
		try (Git git = new Git(db)) {
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			RevCommit commit1 = git.commit().setMessage("add folder a & file b")
					.call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			RevCommit commit2 = git.commit().setMessage("delete folder a").call();

			TreeWalk tw = new TreeWalk(db);
			tw.addTree(commit1.getTree());
			tw.addTree(commit2.getTree());
			List<DiffEntry> scan = DiffEntry.scan(tw);
			assertEquals(1
			assertEquals(FileMode.MISSING
			assertEquals(FileMode.TREE

			writeTrashFile("a"

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			CheckoutConflictException exception = null;
			try {
				git.checkout().setName(branch_1.getName()).call();
			} catch (CheckoutConflictException e) {
				exception = e;
			}
			assertNotNull(exception);
			assertEquals(1
			assertEquals("a"
		}
	}

	@Test
	public void fileModeTestFolderThenFileWithMissingInWorkingTree()
			throws Exception {
		try (Git git = new Git(db)) {
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add folder a & file b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			File fileA = new File(db.getWorkTree()
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			FileUtils.delete(fileA);

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.TREE
		}
	}

	@Test
	public void fileModeTestFileThenFileWithFolderInIndex() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add files a & b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			git.rm().addFilepattern("a").call();
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			git.add().addFilepattern(".").call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.TREE

			CheckoutConflictException exception = null;
			try {
				git.checkout().setName(branch_1.getName()).call();
			} catch (CheckoutConflictException e) {
				exception = e;
			}
			assertNotNull(exception);
			assertEquals(1
			assertEquals("a"
		}
	}

	@Test
	public void fileModeTestFileWithFolderInIndex() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("b"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b & file a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			git.rm().addFilepattern("a").call();
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			git.add().addFilepattern(".").call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.TREE

			CheckoutConflictException exception = null;
			try {
				git.checkout().setName(branch_1.getName()).call();
			} catch (CheckoutConflictException e) {
				exception = e;
			}
			assertNotNull(exception);
			assertEquals(1
			assertEquals("a"

		}
	}

	@Test
	public void testCheckoutPath() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit file a").call();
			git.branchCreate().setName("branch_1").call();
			git.checkout().setName("branch_1").call();
			File b = writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("commit file b").call();
			File a = writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("modified a").call();
			assertArrayEquals(new String[] { "" }
					execute("git checkout HEAD~2 -- a"));
			assertEquals("Hello world a"
			assertArrayEquals(new String[] { "* branch_1"
					execute("git branch"));
			assertEquals("Hello world b"
		}
	}

	@Test
	public void testCheckoutAllPaths() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit file a").call();
			git.branchCreate().setName("branch_1").call();
			git.checkout().setName("branch_1").call();
			File b = writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("commit file b").call();
			File a = writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("modified a").call();
			assertArrayEquals(new String[] { "" }
					execute("git checkout HEAD~2 -- ."));
			assertEquals("Hello world a"
			assertArrayEquals(new String[] { "* branch_1"
					execute("git branch"));
			assertEquals("Hello world b"
		}
	}

	@Test
	public void testCheckoutSingleFile() throws Exception {
		try (Git git = new Git(db)) {
			File a = writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit file a").call();
			writeTrashFile("a"
			assertEquals("b"
			assertEquals("[]"
			assertEquals("file a"
		}
	}

	@Test
	public void testCheckoutLink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		try (Git git = new Git(db)) {
			Path path = writeLink("a"
			assertTrue(Files.isSymbolicLink(path));
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit link a").call();
			deleteTrashFile("a");
			writeTrashFile("a"
			assertFalse(Files.isSymbolicLink(path));
			assertEquals("[]"
			assertEquals("link_a"
			assertTrue(Files.isSymbolicLink(path));
		}
	}

	@Test
	public void testCheckoutForce_Bug530771() throws Exception {
		try (Git git = new Git(db)) {
			File f = writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("create a").call();
			writeTrashFile("a"
			assertEquals("[]"
					Arrays.toString(execute("git checkout -f HEAD")));
			assertEquals("Hello world"
			assertEquals("[a
					indexState(db
		}
	}
}
