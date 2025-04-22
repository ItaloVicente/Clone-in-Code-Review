	public void shouldReturnSourceMergeForSymbolicRef() throws Exception {
		Git git = new Git(repo);
		git.branchCreate().setName("test").setStartPoint("refs/heads/master")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();
		git.checkout().setName("test").call();
		GitSynchronizeData gsd = new GitSynchronizeData(repo, HEAD, HEAD, false);

		String srcMerge = gsd.getSrcMerge();

		assertThat(srcMerge, is("refs/heads/master"));
	}

	@Test
	public void shouldReturnSourceMergeForLocalRef() throws Exception {
		Git git = new Git(repo);
		git.branchCreate().setName("test2").setStartPoint("refs/heads/master")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();
		git.checkout().setName("test2").call();
		GitSynchronizeData gsd = new GitSynchronizeData(repo, R_HEADS + "test2",
				HEAD, false);

		String srcMerge = gsd.getSrcMerge();

		assertThat(srcMerge, is("refs/heads/master"));
	}

	@Test
	public void shouldReturnSourceMergeForRemoteBranch() throws Exception {
