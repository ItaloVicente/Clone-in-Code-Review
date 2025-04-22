package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SymlinkMergeTest extends RepositoryTestCase {

	@Parameters(name = "target={0}
	public static Object[][] parameters() {
		return new Object[][] {
			{ Target.NONE
			{ Target.FILE
			{ Target.DIRECTORY
			{ Target.NONE
			{ Target.FILE
			{ Target.DIRECTORY
		};
	}

	public enum Target {
		NONE
	}

	@Parameter(0)
	public Target target;

	@Parameter(1)
	public boolean useSymLinks;

	private void setTargets() throws IOException {
		switch (target) {
		case DIRECTORY:
			assertTrue(new File(trash
			assertTrue(new File(trash
			assertTrue(new File(trash
			break;
		case FILE:
			writeTrashFile("target"
			writeTrashFile("target1"
			writeTrashFile("target2"
			break;
		default:
			break;
		}
	}

	private void checkTargets() throws IOException {
		File t = new File(trash
		File t1 = new File(trash
		File t2 = new File(trash
		switch (target) {
		case DIRECTORY:
			assertTrue(t.isDirectory());
			assertTrue(t1.isDirectory());
			assertTrue(t2.isDirectory());
			break;
		case FILE:
			checkFile(t
			checkFile(t1
			checkFile(t2
			break;
		default:
			assertFalse(Files.exists(t.toPath()
			assertFalse(Files.exists(t1.toPath()
			assertFalse(Files.exists(t2.toPath()
			break;
		}
	}

	private void assertSymLink(File link
		if (useSymLinks) {
			assertTrue(Files.isSymbolicLink(link.toPath()));
			assertEquals(content
		} else {
			assertFalse(Files.isSymbolicLink(link.toPath()));
			assertTrue(link.isFile());
			checkFile(link
		}
	}


	@Test
	public void mergeWithSymlinkConflict() throws Exception {
		assumeTrue(db.getFS().supportsSymlinks() || !useSymLinks);
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SYMLINKS
		config.save();
		try (TestRepository<Repository> repo = new TestRepository<>(db)) {
			db.incrementOpen();
			RevCommit base = repo
					.commit(repo.tree(repo.link("link"
			RevCommit side = repo.commit(
					repo.tree(repo.link("link"
			RevCommit head = repo.commit(
					repo.tree(repo.link("link"
			try (Git git = new Git(db)) {
				setTargets();
				git.reset().setMode(ResetType.HARD).setRef(head.name()).call();
				File link = new File(trash
				assertSymLink(link
				MergeResult result = git.merge().include(side)
						.setMessage("merged").call();
				assertEquals(MergeStatus.CONFLICTING
				assertSymLink(link
				checkTargets();
				assertEquals("[link
						+ "[link
						+ "[link
						indexState(CONTENT));
			}
		}
	}


	@Test
	public void mergeWithFileSymlinkConflict() throws Exception {
		assumeTrue(db.getFS().supportsSymlinks() || !useSymLinks);
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SYMLINKS
		config.save();
		try (TestRepository<Repository> repo = new TestRepository<>(db)) {
			db.incrementOpen();
			RevCommit base = repo.commit(repo.tree());
			RevCommit side = repo.commit(
					repo.tree(repo.link("link"
			RevCommit head = repo.commit(
					repo.tree(repo.file("link"
					base);
			try (Git git = new Git(db)) {
				setTargets();
				git.reset().setMode(ResetType.HARD).setRef(head.name()).call();
				File link = new File(trash
				assertFalse(Files.isSymbolicLink(link.toPath()));
				checkFile(link
				MergeResult result = git.merge().include(side)
						.setMessage("merged").call();
				assertEquals(MergeStatus.CONFLICTING
				assertFalse(Files.isSymbolicLink(link.toPath()));
				checkFile(link
				checkTargets();
				assertEquals("[link
						+ "[link
						indexState(CONTENT));
			}
		}
	}

	@Test
	public void mergeWithSymlinkFileConflict() throws Exception {
		assumeTrue(db.getFS().supportsSymlinks() || !useSymLinks);
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SYMLINKS
		config.save();
		try (TestRepository<Repository> repo = new TestRepository<>(db)) {
			db.incrementOpen();
			RevCommit base = repo.commit(repo.tree());
			RevCommit side = repo.commit(
					repo.tree(repo.file("link"
					base);
			RevCommit head = repo.commit(
					repo.tree(repo.link("link"
			try (Git git = new Git(db)) {
				setTargets();
				git.reset().setMode(ResetType.HARD).setRef(head.name()).call();
				File link = new File(trash
				assertSymLink(link
				MergeResult result = git.merge().include(side)
						.setMessage("merged").call();
				assertEquals(MergeStatus.CONFLICTING
				assertFalse(Files.isSymbolicLink(link.toPath()));
				checkFile(link
				checkTargets();
				assertEquals("[link
						+ "[link
						indexState(CONTENT));
			}
		}
	}


	@Test
	public void mergeWithSymlinkDeleteModify() throws Exception {
		assumeTrue(db.getFS().supportsSymlinks() || !useSymLinks);
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SYMLINKS
		config.save();
		try (TestRepository<Repository> repo = new TestRepository<>(db)) {
			db.incrementOpen();
			RevCommit base = repo
					.commit(repo.tree(repo.link("link"
			RevCommit side = repo.commit(
					repo.tree(repo.link("link"
			RevCommit head = repo.commit(repo.tree()
			try (Git git = new Git(db)) {
				setTargets();
				git.reset().setMode(ResetType.HARD).setRef(head.name()).call();
				File link = new File(trash
				assertFalse(
						Files.exists(link.toPath()
				MergeResult result = git.merge().include(side)
						.setMessage("merged").call();
				assertEquals(MergeStatus.CONFLICTING
				assertSymLink(link
				checkTargets();
				assertEquals("[link
						+ "[link
						indexState(CONTENT));
			}
		}
	}

	@Test
	public void mergeWithSymlinkModifyDelete() throws Exception {
		assumeTrue(db.getFS().supportsSymlinks() || !useSymLinks);
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SYMLINKS
		config.save();
		try (TestRepository<Repository> repo = new TestRepository<>(db)) {
			db.incrementOpen();
			RevCommit base = repo
					.commit(repo.tree(repo.link("link"
			RevCommit side = repo.commit(repo.tree()
			RevCommit head = repo.commit(
					repo.tree(repo.link("link"
			try (Git git = new Git(db)) {
				setTargets();
				git.reset().setMode(ResetType.HARD).setRef(head.name()).call();
				File link = new File(trash
				assertSymLink(link
				MergeResult result = git.merge().include(side)
						.setMessage("merged").call();
				assertEquals(MergeStatus.CONFLICTING
				assertSymLink(link
				checkTargets();
				assertEquals("[link
						+ "[link
						indexState(CONTENT));
			}
		}
	}
}
