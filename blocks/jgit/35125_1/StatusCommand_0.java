
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.IndexDiff.IgnoreSubmoduleMode;
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
	public static IgnoreSubmoduleMode mode[] = IgnoreSubmoduleMode.values();

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
				.setPath("submodule")
				.setURI(submoduleStandalone.getDirectory().toURI().toString())
				.call();
		submodule_trash = submodule_db.getWorkTree();
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

	@Theory
	public void testDirtyRootWorktree(IgnoreSubmoduleMode mode)
			throws IOException {
		writeTrashFile("fileInRoot"

		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertTrue(indexDiff.diff());
	}

	@Theory
	public void testDirtySubmoduleWorktree(IgnoreSubmoduleMode mode)
			throws IOException {
		JGitTestUtil.writeTrashFile(submodule_db
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		if (mode.equals(IgnoreSubmoduleMode.ALL)
				|| mode.equals(IgnoreSubmoduleMode.DIRTY))
			assertFalse("diff should be false with mode=" + mode
					indexDiff.diff());
		else
			assertTrue("diff should be true with mode=" + mode
					indexDiff.diff());
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
		if (mode.equals(IgnoreSubmoduleMode.ALL))
			assertFalse("diff should be false with mode=" + mode
					indexDiff.diff());
		else
			assertTrue("diff should be true with mode=" + mode
					indexDiff.diff());
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
		if (mode.equals(IgnoreSubmoduleMode.ALL)
				|| mode.equals(IgnoreSubmoduleMode.DIRTY))
			assertFalse("diff should be false with mode=" + mode
					indexDiff.diff());
		else
			assertTrue("diff should be true with mode=" + mode
					indexDiff.diff());
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
		if (mode.equals(IgnoreSubmoduleMode.ALL)
				|| mode.equals(IgnoreSubmoduleMode.DIRTY))
			assertFalse("diff should be false with mode=" + mode
					indexDiff.diff());
		else
			assertTrue("diff should be true with mode=" + mode
					indexDiff.diff());
	}

	@Theory
	public void testDirtySubmoduleWorktreeUntracked(IgnoreSubmoduleMode mode)
			throws IOException {
		JGitTestUtil.writeTrashFile(submodule_db
				"2");
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		if (mode.equals(IgnoreSubmoduleMode.ALL)
				|| mode.equals(IgnoreSubmoduleMode.DIRTY)
				|| mode.equals(IgnoreSubmoduleMode.UNTRACKED))
			assertFalse("diff should be false with mode=" + mode
					indexDiff.diff());
		else
			assertTrue("diff should be true with mode=" + mode
					indexDiff.diff());
	}
}
