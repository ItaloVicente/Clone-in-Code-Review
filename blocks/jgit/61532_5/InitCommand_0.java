
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.Before;

public class RepositoryBuilderTest {

	private RepositoryBuilder builder;

	private File NONBAREGITDIR;

	private File BAREGITDIR;

	private File WORKTREE;

	@SuppressWarnings("deprecation")
	private void createRepoAndAssert(boolean callCreateWithBareParam
			boolean bare) throws IOException {
		final Repository repo = builder.build();
		if (callCreateWithBareParam) {
			repo.create(bare);
		} else {
			repo.create();
		}
		assertEquals(Boolean.valueOf(builder.isBare())
		assertEquals(Boolean.valueOf(repo.isBare())
	}

	@Before
	public void setUp() throws IOException {
		builder = new RepositoryBuilder();
		NONBAREGITDIR = new File(
				Files.createTempDirectory("JGitRepositoryCreateTest").toFile()
				".git");
		BAREGITDIR = new File(
				Files.createTempDirectory("JGitRepositoryCreateTest").toFile()
				"GITDIR");
		WORKTREE = new File(
				Files.createTempDirectory("JGitRepositoryCreateTest").toFile()
				"WORKTREE");
	}

	@Test(expected = IllegalArgumentException.class)
	public void noParameter_build_Test() throws IOException {
		builder.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void justSetBare_build_Test() throws IOException {
		builder.setBare().build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWorkTree_setBare_build_Test() throws IOException {
		builder.setWorkTree(WORKTREE).setBare().build();
	}

	@Test()
	public void setGitDirImplicitNonBare_create_Test() throws IOException {
		builder.setGitDir(NONBAREGITDIR);
	}

	@Test()
	public void setGitDirImplicitNonBare_createNonBare_Test()
			throws IOException {
		builder.setGitDir(NONBAREGITDIR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setGitDirImplicitNonBare_createBare_Test() throws IOException {
		builder.setGitDir(NONBAREGITDIR);
	}

	@Test()
	public void setGitDirImplicitBare_create_Test() throws IOException {
		builder.setGitDir(BAREGITDIR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setGitDirImplicitBare_createNonBare_Test() throws IOException {
		builder.setGitDir(BAREGITDIR);
	}

	@Test()
	public void setGitDirImplicitBare_createBare_Test() throws IOException {
		builder.setGitDir(BAREGITDIR);
	}

	@Test()
	public void setWorkTree_create_Test() throws IOException {
		builder.setWorkTree(WORKTREE);
	}

	@Test()
	public void setWorkTree_createNonBare_Test() throws IOException {
		builder.setWorkTree(WORKTREE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWorkTree_createBare_Test() throws IOException {
		builder.setWorkTree(WORKTREE);
	}

	@Test()
	public void setBare_setGitDir_create_Test() throws IOException {
		builder.setBare().setGitDir(NONBAREGITDIR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBare_setGitDir_createNonBare_Test() throws IOException {
		builder.setBare().setGitDir(NONBAREGITDIR);
	}

	@Test()
	public void setBare_setGitDir_createBare_Test() throws IOException {
		builder.setBare().setGitDir(NONBAREGITDIR);

	}

	@Test()
	public void setBare_setWorkTree_create_Test() throws IOException {
		builder.setBare().setWorkTree(WORKTREE);
	}

	@Test()
	public void setBare_setWorkTree_createNonBare_Test() throws IOException {
		builder.setBare().setWorkTree(WORKTREE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBare_setWorkTree_createBare_Test() throws IOException {
		builder.setBare().setWorkTree(WORKTREE);
	}

	public void setGitDir_setWorkTree_create_Test() throws IOException {
		builder.setGitDir(BAREGITDIR).setWorkTree(WORKTREE);
	}

	@Test()
	public void setGitDir_setWorkTree_createNonBare_Test() throws IOException {
		builder.setGitDir(BAREGITDIR).setBare().setWorkTree(WORKTREE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setGitDir_setWorkTree_createBare_Test() throws IOException {
		builder.setGitDir(BAREGITDIR).setWorkTree(WORKTREE);
	}


	@Test()
	public void setBare_setGitDir_setWorkTree_create_Test() throws IOException {
		builder.setBare().setGitDir(NONBAREGITDIR).setWorkTree(WORKTREE);
	}

	@Test()
	public void setBare_setGitDir_setWorkTree_createNonBare_Test()
			throws IOException {
		builder.setBare().setGitDir(NONBAREGITDIR).setWorkTree(WORKTREE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBare_setGitDir_setWorkTree_createBare_Test()
			throws IOException {
		builder.setBare().setGitDir(NONBAREGITDIR).setWorkTree(WORKTREE);
	}

	@Test()
	public void setGitDir_setWorkTree_setBare_create_Test() throws IOException {
		builder.setGitDir(NONBAREGITDIR).setWorkTree(WORKTREE).setBare();
	}

	@Test(expected = IllegalArgumentException.class)
	public void setGitDir_setWorkTree_setBare_createNonBare_Test()
			throws IOException {
		builder.setGitDir(NONBAREGITDIR).setWorkTree(WORKTREE).setBare();
	}

	@Test()
	public void setGitDir_setWorkTree_setBare_createBare_Test()
			throws IOException {
		builder.setGitDir(NONBAREGITDIR).setWorkTree(WORKTREE).setBare();
	}
}
