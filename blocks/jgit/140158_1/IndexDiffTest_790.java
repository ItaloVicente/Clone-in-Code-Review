
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.submodule.SubmoduleWalk.IgnoreSubmoduleMode;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class IndexDiffSubmoduleTest extends RepositoryTestCase {
	protected FileRepository submodule_db;

	protected File submodule_trash;

	@DataPoints
	public static IgnoreSubmoduleMode allModes[] = IgnoreSubmoduleMode.values();

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		FileRepository submoduleStandalone = createWorkRepository();
		JGitTestUtil.writeTrashFile(submoduleStandalone
				"submodule");
		Git submoduleStandaloneGit = Git.wrap(submoduleStandalone);
		submoduleStandaloneGit.add().addFilepattern("fileInSubmodule").call();
		submoduleStandaloneGit.commit().setMessage("add file to submodule")
				.call();

		submodule_db = (FileRepository) Git.wrap(db).submoduleAdd()
				.setPath("modules/submodule")
				.setURI(submoduleStandalone.getDirectory().toURI().toString())
				.call();
		submodule_trash = submodule_db.getWorkTree();
		addRepoToClose(submodule_db);
		writeTrashFile("fileInRoot"
		Git rootGit = Git.wrap(db);
		rootGit.add().addFilepattern("fileInRoot").call();
		rootGit.commit().setMessage("add submodule and root file").call();
	}

	@Theory
	public void testInitiallyClean(IgnoreSubmoduleMode mode)
			throws IOException {
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertFalse(indexDiff.diff());
	}

	private Repository cloneWithoutCloningSubmodule() throws Exception {
		File directory = createTempDirectory(
				"testCloneWithoutCloningSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(false);
		clone.setURI(db.getDirectory().toURI().toString());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		return git2.getRepository();
	}

	@Theory
	public void testCleanAfterClone(IgnoreSubmoduleMode mode) throws Exception {
		Repository db2 = cloneWithoutCloningSubmodule();
		IndexDiff indexDiff = new IndexDiff(db2
				new FileTreeIterator(db2));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertFalse(indexDiff.diff());
	}

	@Theory
	public void testMissingIfDirectoryGone(IgnoreSubmoduleMode mode)
			throws Exception {
		recursiveDelete(submodule_trash);
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		boolean hasChanges = indexDiff.diff();
		if (mode != IgnoreSubmoduleMode.ALL) {
			assertTrue(hasChanges);
			assertEquals("[modules/submodule]"
					indexDiff.getMissing().toString());
		} else {
			assertFalse(hasChanges);
		}
	}

	@Theory
	public void testSubmoduleReplacedByFile(IgnoreSubmoduleMode mode)
			throws Exception {
		recursiveDelete(submodule_trash);
		writeTrashFile("modules/submodule"
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertTrue(indexDiff.diff());
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[modules/submodule]"
	}

	@Theory
	public void testDirtyRootWorktree(IgnoreSubmoduleMode mode)
			throws IOException {
		writeTrashFile("fileInRoot"

		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertTrue(indexDiff.diff());
	}

	private void assertDiff(IndexDiff indexDiff
			IgnoreSubmoduleMode... expectedEmptyModes) throws IOException {
		boolean diffResult = indexDiff.diff();
		Set<String> submodulePaths = indexDiff
				.getPathsWithIndexMode(FileMode.GITLINK);
		boolean emptyExpected = false;
		for (IgnoreSubmoduleMode empty : expectedEmptyModes) {
			if (mode.equals(empty)) {
				emptyExpected = true;
				break;
			}
		}
		if (emptyExpected) {
			assertFalse("diff should be false with mode=" + mode
					diffResult);
			assertEquals("should have no paths with FileMode.GITLINK"
					submodulePaths.size());
		} else {
			assertTrue("diff should be true with mode=" + mode
					diffResult);
			assertTrue("submodule path should have FileMode.GITLINK"
					submodulePaths.contains("modules/submodule"));
		}
	}

	@Theory
	public void testDirtySubmoduleWorktree(IgnoreSubmoduleMode mode)
			throws IOException {
		JGitTestUtil.writeTrashFile(submodule_db
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertDiff(indexDiff
				IgnoreSubmoduleMode.DIRTY);
	}

	@Theory
	public void testDirtySubmoduleHEAD(IgnoreSubmoduleMode mode)
			throws IOException
		JGitTestUtil.writeTrashFile(submodule_db
		Git submoduleGit = Git.wrap(submodule_db);
		submoduleGit.add().addFilepattern("fileInSubmodule").call();
		submoduleGit.commit().setMessage("Modified fileInSubmodule").call();

		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertDiff(indexDiff
	}

	@Theory
	public void testDirtySubmoduleIndex(IgnoreSubmoduleMode mode)
			throws IOException
		JGitTestUtil.writeTrashFile(submodule_db
		Git submoduleGit = Git.wrap(submodule_db);
		submoduleGit.add().addFilepattern("fileInSubmodule").call();

		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertDiff(indexDiff
				IgnoreSubmoduleMode.DIRTY);
	}

	@Theory
	public void testDirtySubmoduleIndexAndWorktree(IgnoreSubmoduleMode mode)
			throws IOException
		JGitTestUtil.writeTrashFile(submodule_db
		Git submoduleGit = Git.wrap(submodule_db);
		submoduleGit.add().addFilepattern("fileInSubmodule").call();
		JGitTestUtil.writeTrashFile(submodule_db

		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertDiff(indexDiff
				IgnoreSubmoduleMode.DIRTY);
	}

	@Theory
	public void testDirtySubmoduleWorktreeUntracked(IgnoreSubmoduleMode mode)
			throws IOException {
		JGitTestUtil.writeTrashFile(submodule_db
				"2");
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertDiff(indexDiff
				IgnoreSubmoduleMode.DIRTY
	}
}
