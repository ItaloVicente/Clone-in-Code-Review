
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class RepositorySetupWorkDirTest extends LocalDiskRepositoryTestCase {

	@Test
	public void testIsBare_CreateRepositoryFromArbitraryGitDir()
			throws Exception {
		File gitDir = getFile("workdir");
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		assertTrue(repo.isBare());
	}

	@Test
	public void testNotBare_CreateRepositoryFromDotGitGitDir() throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	@Test
	public void testWorkdirIsParentDir_CreateRepositoryFromDotGitGitDir()
			throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		String workdir = repo.getWorkTree().getName();
		assertEquals(workdir
	}

	@Test
	public void testNotBare_CreateRepositoryFromWorkDirOnly() throws Exception {
		File workdir = getFile("workdir"
		Repository repo = new FileRepositoryBuilder().setWorkTree(workdir)
				.build();
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	@Test
	public void testWorkdirIsDotGit_CreateRepositoryFromWorkDirOnly()
			throws Exception {
		File workdir = getFile("workdir"
		Repository repo = new FileRepositoryBuilder().setWorkTree(workdir)
				.build();
		assertGitdirPath(repo
	}

	@Test
	public void testNotBare_CreateRepositoryFromGitDirOnlyWithWorktreeConfig()
			throws Exception {
		File gitDir = getFile("workdir"
		File workTree = getFile("workdir"
		setWorkTree(gitDir
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	@Test
	public void testBare_CreateRepositoryFromGitDirOnlyWithBareConfigTrue()
			throws Exception {
		File gitDir = getFile("workdir"
		setBare(gitDir
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		assertTrue(repo.isBare());
	}

	@Test
	public void testWorkdirIsParent_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		File gitDir = getFile("workdir"
		setBare(gitDir
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		assertWorkdirPath(repo
	}

	@Test
	public void testNotBare_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		File gitDir = getFile("workdir"
		setBare(gitDir
		Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	@Test
	public void testExceptionThrown_BareRepoGetWorkDir() throws Exception {
		File gitDir = getFile("workdir");
		try (Repository repo = new FileRepository(gitDir)) {
			repo.getWorkTree();
			fail("Expected NoWorkTreeException missing");
		} catch (NoWorkTreeException e) {
		}
	}

	@Test
	public void testExceptionThrown_BareRepoGetIndex() throws Exception {
		File gitDir = getFile("workdir");
		try (Repository repo = new FileRepository(gitDir)) {
			repo.readDirCache();
			fail("Expected NoWorkTreeException missing");
		} catch (NoWorkTreeException e) {
		}
	}

	@Test
	public void testExceptionThrown_BareRepoGetIndexFile() throws Exception {
		File gitDir = getFile("workdir");
		try (Repository repo = new FileRepository(gitDir)) {
			repo.getIndexFile();
			fail("Expected NoWorkTreeException missing");
		} catch (NoWorkTreeException e) {
		}
	}

	private File getFile(String... pathComponents) throws IOException {
		File dir = getTemporaryDirectory();
		for (String pathComponent : pathComponents)
			dir = new File(dir
		FileUtils.mkdirs(dir
		return dir;
	}

	private void setBare(File gitDir
			ConfigInvalidException {
		FileBasedConfig cfg = configFor(gitDir);
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		cfg.save();
	}

	private void setWorkTree(File gitDir
			throws IOException
			ConfigInvalidException {
		String path = workTree.getAbsolutePath();
		FileBasedConfig cfg = configFor(gitDir);
		cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_WORKTREE
		cfg.save();
	}

	private FileBasedConfig configFor(File gitDir) throws IOException
			ConfigInvalidException {
		File configPath = new File(gitDir
		FileBasedConfig cfg = new FileBasedConfig(configPath
		cfg.load();
		return cfg;
	}

	private void assertGitdirPath(Repository repo
			throws IOException {
		File exp = getFile(expected).getCanonicalFile();
		File act = repo.getDirectory().getCanonicalFile();
		assertEquals("Wrong Git Directory"
	}

	private void assertWorkdirPath(Repository repo
			throws IOException {
		File exp = getFile(expected).getCanonicalFile();
		File act = repo.getWorkTree().getCanonicalFile();
		assertEquals("Wrong working Directory"
	}
}
