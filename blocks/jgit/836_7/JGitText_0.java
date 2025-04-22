
package org.eclipse.jgit.lib;

import java.io.File;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;

public class RepositoryTest extends LocalDiskRepositoryTestCase {

	public void test001_CreateRepositoryFromArbitraryGitDir() throws Exception {
		setUp();
		File gitDir = getFile("workdir");
		assertTrue("Repository on arbitrary Git dir should be bare"
				new Repository(gitDir).isBare());
		tearDown();
	}

	public void test002_CreateRepositoryFromDotGitGitDir() throws Exception {
		setUp();
		File gitDir = getFile("workdir"
		assertFalse("Repository on dotgit Git dir should not be bare"
				new Repository(gitDir).isBare());
		tearDown();
	}

	public void test003_CreateRepositoryFromWorkDirOnly() throws Exception {
		setUp();
		File workdir = getFile("workdir"
		Repository repo = new Repository(null
		assertFalse("Repository on arbitrary workdir should not be bare"
				.isBare());
		tearDown();
	}

	public void test004_CreateRepositoryFromWorkDirOnly() throws Exception {
		setUp();
		File workdir = getFile("workdir"
		Repository repo = new Repository(null
		assertTrue("Implicit directory should be dotgit"
				.getPath().endsWith(Constants.DOT_GIT));
		tearDown();
	}

	public void test005_CreateRepositoryFromGitDirOnlyWithWorktreeConfig()
			throws Exception {
		setUp();
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir

		repo.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_WORKTREE
		repo.getConfig().save();

		repo = new Repository(gitDir

		assertFalse(
				"Repository on arbitrary Git dir with worktree config should not be bare"
				repo.isBare());
		repo.close();

		tearDown();
	}

	public void test006_CreateRepositoryFromGitDirOnlyWithBareConfigTrue()
			throws Exception {
		setUp();
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir

		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();

		repo = new Repository(gitDir

		assertTrue(
				"Repository on arbitrary Git dir with bare config true should be bare"
				repo.isBare());
		repo.close();

		tearDown();
	}

	public void test007_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		setUp();
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir

		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();

		repo = new Repository(gitDir

		String workdir = repo.getWorkDir().getName();

		assertEquals(
				"Working dir of Repository on arbitrary Git dir with bare config false should not be parent"
				"repoWithBareConfigTrue"
		repo.close();

		tearDown();
	}

	public void test008_CreateRepositoryFromGitDirOnlyWithBareConfigFalse()
			throws Exception {
		setUp();
		File gitDir = getFile("workdir"
		Repository repo = new Repository(gitDir

		repo.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		repo.getConfig().save();

		repo = new Repository(gitDir

		assertFalse(
				"Repository on arbitrary Git dir with bare config false should not be bare"
				repo.isBare());

		repo.close();

		tearDown();
	}

	public void test009_MakeBareUnbareBySetWorkdir() throws Exception {
		setUp();
		File gitDir = getFile("gitDir");
		Repository repo = new Repository(gitDir);
		repo.setWorkDir(getFile("workingDir"));
		assertFalse("Repository on arbitrary Git dir should not be bare"
				.isBare());

		tearDown();
	}

	public void test010_AssertExceptionGetWorkDir() throws Exception {
		setUp();
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getWorkDir();
			fail("Expected Exception missing");
		} catch (Exception e) {
		}
		tearDown();
	}

	public void test011_AssertExceptionGetIndex() throws Exception {
		setUp();
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getIndex();
			fail("Expected Exception missing");
		} catch (Exception e) {
		}
		tearDown();
	}

	public void test012_AssertExceptionGetIndexFile() throws Exception {
		setUp();
		File gitDir = getFile("workdir");
		try {
			new Repository(gitDir).getIndexFile();
			fail("Expected Exception missing");
		} catch (Exception e) {
		}
		tearDown();
	}

	public File getFile(String... pathComponents) {
		String rootPath = new File(new File("target")
		for (String pathComponent : pathComponents)
			rootPath = rootPath + File.separatorChar + pathComponent;
		File result = new File(rootPath);
		result.mkdir();
		return result;
	}

}
