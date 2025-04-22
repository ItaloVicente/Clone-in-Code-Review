	@Test
	public void testMergeConflict() throws Exception {
		Git git = new Git(repository);
		git.checkout().setCreateBranch(true).setName("side").call();
		commitOneFileChange("on side");

		git.checkout().setName("master").call();
		commitOneFileChange("on master");

		git.merge().include(repository.getRef("side")).call();
		assertEquals(RepositoryState.MERGING, repository.getRepositoryState());

		StagingViewTester stagingView = StagingViewTester
				.openStagingView();
		assertEquals("", stagingView.getCommitMessage());
		stagingView.assertCommitEnabled(false);

		setContent("resolved");
		stagingView.stageFile(FILE1_PATH);
		assertEquals(RepositoryState.MERGING_RESOLVED,
				repository.getRepositoryState());
		String expectedMessage = "Merge branch 'side'";
		assertThat(stagingView.getCommitMessage(), startsWith(expectedMessage));

		stagingView.commit();
		assertEquals(RepositoryState.SAFE, repository.getRepositoryState());

		assertEquals(expectedMessage, TestUtil.getHeadCommit(repository)
				.getShortMessage());
	}

