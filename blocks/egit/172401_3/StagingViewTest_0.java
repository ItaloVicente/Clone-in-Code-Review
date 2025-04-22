	@Test
	public void testMergeConflictCheckoutHead() throws Exception {
		try (Git git = new Git(repository)) {
			git.checkout().setCreateBranch(true).setName("side").call();
			commitOneFileChange("on side");

			git.checkout().setName("master").call();
			commitOneFileChange("on master");

			git.merge().include(repository.findRef("side")).call();
		}
		assertEquals(RepositoryState.MERGING, repository.getRepositoryState());

		StagingViewTester stagingView = StagingViewTester.openStagingView();
		assertEquals("", stagingView.getCommitMessage());
		stagingView.assertCommitEnabled(false);

		SWTBot viewBot = stagingView.getView().bot();
		SWTBotTree unstagedTree = viewBot.tree(0);

		TestUtil.waitUntilTreeHasNodeContainsText(viewBot, unstagedTree,
				FILE1_PATH, 10000);

		TestUtil.getNode(unstagedTree.getAllItems(), FILE1_PATH).select();

		JobJoiner jobJoiner = JobJoiner.startListening(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE, 30,
				TimeUnit.SECONDS);

		ContextMenuHelper.clickContextMenu(unstagedTree,
				UIText.StagingView_replaceWithHeadRevision);

		jobJoiner.join();
		TestUtil.processUIEvents();

		assertEquals(RepositoryState.MERGING_RESOLVED,
				repository.getRepositoryState());
		assertEquals("on master", getTestFileContent());
		String expectedMessage = "Merge branch 'side'";
		assertThat(stagingView.getCommitMessage(), startsWith(expectedMessage));

		jobJoiner = JobJoiner.startListening(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE, 30,
				TimeUnit.SECONDS);
		stagingView.commit();
		jobJoiner.join();
		TestUtil.processUIEvents();

		assertEquals(RepositoryState.SAFE, repository.getRepositoryState());

		assertEquals(expectedMessage,
				TestUtil.getHeadCommit(repository).getShortMessage());
	}

