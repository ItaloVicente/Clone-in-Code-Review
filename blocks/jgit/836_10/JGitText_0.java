
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;

public class RepositorySetupWorkDirTest extends LocalDiskRepositoryTestCase {

	public void testIsBare_CreateRepositoryFromArbitraryGitDir()
			throws Exception {
		File gitDir = getFile("workdir");
		assertTrue(new Repository(gitDir).isBare());
	}

	public void testNotBare_CreateRepositoryFromDotGitGitDir() throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir);
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	public void testWorkdirIsParentDir_CreateRepositoryFromDotGitGitDir()
			throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir);
		String workdir = repo.getWorkDir().getName();
		assertEquals(workdir
	}

	public void testNotBare_CreateRepositoryFromWorkDirOnly() throws Exception {
		File workdir = getFile("workdir"
		Repository repo = new Repository(null
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	public void testWorkdirIsDotGit_CreateRepositoryFromWorkDirOnly()
			throws Exception {
		File workdir = getFile("workdir"
		Repository repo = new Repository(null
		assertGitdirPath(repo
	}

	public void testNotBare_CreateRepositoryFromGitDirOnlyWithWorktreeConfig()
			throws Exception {
		File gitDir = getFile("workdir"
		File workTree = getFile("workdir"
		setWorkTree(gitDir
		Repository repo = new Repository(gitDir
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	public void testBare_CreateRepositoryFromGitDirOnlyWithBareConfigTrue()
			throws Exception {
		File gitDir = getFile("workdir"
		setBare(gitDir
		Repository repo = new Repository(gitDir
		assertTrue(repo.isBare());
	}

	public void testWorkdirIsParent_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		File gitDir = getFile("workdir"
		setBare(gitDir
		Repository repo = new Repository(gitDir
		assertWorkdirPath(repo
	}

	public void testNotBare_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		File gitDir = getFile("workdir"
		setBare(gitDir
		Repository repo = new Repository(gitDir
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	public void testNotBare_MakeBareUnbareBySetWorkdir() throws Exception {
		File gitDir = getFile("gitDir");
		Repository repo = new Repository(gitDir);
		repo.setWorkDir(getFile("workingDir"));
		assertFalse(repo.isBare());
		assertWorkdirPath(repo
		assertGitdirPath(repo
	}

	public void testExceptionThrown_BareRepoGetWorkDir() throws Exception {
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getWorkDir();
			fail("Expected IllegalStateException missing");
		} catch (IllegalStateException e) {
		}
	}

	public void testExceptionThrown_BareRepoGetIndex() throws Exception {
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getIndex();
			fail("Expected IllegalStateException missing");
		} catch (IllegalStateException e) {
		}
	}

	public void testExceptionThrown_BareRepoGetIndexFile() throws Exception {
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getIndexFile();
			fail("Expected Exception missing");
		} catch (IllegalStateException e) {
		}
	}

	private File getFile(String... pathComponents) {
		String rootPath = new File(new File("target")
		for (String pathComponent : pathComponents)
			rootPath = rootPath + File.separatorChar + pathComponent;
		File result = new File(rootPath);
		result.mkdir();
		return result;
	}

	private void setBare(File gitDir
		Repository repo = new Repository(gitDir
		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();
	}

	private void setWorkTree(File gitDir
		Repository repo = new Repository(gitDir
		repo.getConfig()
				.setString(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_WORKTREE
						workTree.getAbsolutePath());
		repo.getConfig().save();
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
		File act = repo.getWorkDir().getCanonicalFile();
		assertEquals("Wrong working Directory"
	}
}
