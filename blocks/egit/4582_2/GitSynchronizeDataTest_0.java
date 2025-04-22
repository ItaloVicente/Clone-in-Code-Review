	@Test
	public void shouldCreateGitSynchronizeDataForRemoteBranchWithoutRemoteName()
			throws Exception {
		Git git = new Git(repo);
		String remoteBranchName = R_REMOTES + "no-remote-name";
		git.branchCreate().setName(remoteBranchName).call();

		GitSynchronizeData gsd = new GitSynchronizeData(repo, HEAD,
				remoteBranchName, false);

		assertThat(gsd.getDstRemoteName(), nullValue());
		assertThat(gsd.getDstRevCommit().getId(),
				is(repo.resolve(remoteBranchName)));
	}

