
package org.eclipse.jgit.lib;

import java.io.File;

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
		assertWorkdirName(repo
		assertGitdirName(repo
	}

	private void assertGitdirName(Repository repo
		assertTrue("Wrong Git directory name"
				.equals(expected));
	}

	private void assertWorkdirName(Repository repo
		assertTrue("Wrong working directory name"
				.equals(expected));
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
		assertWorkdirName(repo
		assertGitdirName(repo

	}

	public void testWorkdirIsDotGit_CreateRepositoryFromWorkDirOnly()
			throws Exception {
		File workdir = getFile("workdir"
		Repository repo = new Repository(null
		assertTrue(repo.getDirectory().getName().equals(Constants.DOT_GIT));
	}

	public void testNotBare_CreateRepositoryFromGitDirOnlyWithWorktreeConfig()
			throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir
		repo.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_WORKTREE
		repo.getConfig().save();
		repo = new Repository(gitDir
		assertFalse(repo.isBare());
		assertWorkdirName(repo
		assertGitdirName(repo
	}

	public void testBare_CreateRepositoryFromGitDirOnlyWithBareConfigTrue()
			throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir
		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();

		repo = new Repository(gitDir
		assertTrue(repo.isBare());
	}

	public void testWorkdirIsParent_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir
		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();
		repo = new Repository(gitDir
		String workdir = repo.getWorkDir().getName();
		assertEquals("repoWithBareConfigTrue"
	}

	public void testNotBare_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir

		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();
		repo = new Repository(gitDir
		assertFalse(repo.isBare());
		assertWorkdirName(repo
		assertGitdirName(repo
	}

	public void testNotBare_MakeBareUnbareBySetWorkdir() throws Exception {
		File gitDir = getFile("gitDir");
		Repository repo = new Repository(gitDir);
		repo.setWorkDir(getFile("workingDir"));
		assertFalse(repo.isBare());
		assertWorkdirName(repo
		assertGitdirName(repo
	}

	public void testExceptionThrown_BareRepoGetWorkDir() throws Exception {
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getWorkDir();
			fail("Expected Exception missing");
		} catch (IllegalStateException e) {
		}
	}

	public void testExceptionThrown_BareRepoGetIndex() throws Exception {
		setUp();
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getIndex();
			fail("Expected Exception missing");
		} catch (IllegalStateException e) {
		}
		tearDown();
	}

	public void testExceptionThrown_BareRepoGetIndexFile() throws Exception {
		setUp();
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getIndexFile();
			fail("Expected Exception missing");
		} catch (IllegalStateException e) {
		}
		tearDown();
	}

	private File getFile(String... pathComponents) {
		String rootPath = new File(new File("target")
		for (String pathComponent : pathComponents)
			rootPath = rootPath + File.separatorChar + pathComponent;
		File result = new File(rootPath);
		result.mkdir();
		return result;
	}

}
