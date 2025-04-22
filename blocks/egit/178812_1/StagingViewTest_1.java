	@Test
	public void testModifyDeleteConflict() throws Exception {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE1);
		RevCommit side;
		try (Git git = new Git(repository)) {
			git.checkout().setCreateBranch(true).setName("side").call();
			assertTrue(file.exists());
			file.delete(true, null);
			assertFalse(file.exists());
			git.rm().addFilepattern(FILE1_PATH).call();
			TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
			side = git.commit().setMessage("File deleted").call();
			TestUtil.waitForJobs(50, 5000);

			git.checkout().setName("master").call();
			commitOneFileChange("on master");

			git.merge().include(repository.findRef("side")).call();
		}
		assertEquals(RepositoryState.MERGING, repository.getRepositoryState());

		StagingViewTester stagingView = StagingViewTester.openStagingView();
		assertEquals("", stagingView.getCommitMessage());
		stagingView.assertCommitEnabled(false);

		SWTBotView view = stagingView.getView();
		SWTBot viewBot = view.bot();
		SWTBotTree unstagedTree = viewBot.tree(0);

		TestUtil.waitUntilTreeHasNodeContainsText(viewBot, unstagedTree,
				FILE1_PATH, 10000);

		SWTBotTreeItem item = TestUtil.getNode(unstagedTree.getAllItems(),
				FILE1_PATH);
		assertEquals(UIText.StagingView_Conflict_MD_short, item.cell(1));
		item.select();

		JobJoiner jobJoiner = JobJoiner.startListening(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE, 30,
				TimeUnit.SECONDS);

		ContextMenuHelper.clickContextMenu(unstagedTree,
				UIText.StagingView_ReplaceWith,
				formatCommit(
						UIText.ReplaceWithOursTheirsMenu_TheirsWithCommitLabel,
						side));

		jobJoiner.join();

		assertFalse(file.exists());

		assertEquals(RepositoryState.MERGING_RESOLVED,
				repository.getRepositoryState());
		String expectedMessage = "Merge branch 'side'";
		assertThat(stagingView.getCommitMessage(), startsWith(expectedMessage));

		SWTBotTree stagedTree = viewBot.tree(1);
		TestUtil.waitUntilTreeHasNodeContainsText(viewBot, stagedTree,
				FILE1_PATH, 10000);
		SWTBotTreeItem item2 = TestUtil.getNode(stagedTree.getAllItems(),
				FILE1_PATH);
		Object data = UIThreadRunnable.syncExec(() -> item2.widget.getData());
		assertEquals(StagingEntry.class, data.getClass());
		StagingEntry entry = (StagingEntry) data;
		assertEquals(StagingEntry.State.REMOVED, entry.getState());

		stagingView.commit();
		assertEquals(RepositoryState.SAFE, repository.getRepositoryState());

		assertEquals(expectedMessage,
				TestUtil.getHeadCommit(repository).getShortMessage());
	}

	private static String formatCommit(String format, RevCommit commit) {
		String message = Utils.shortenText(commit.getShortMessage(), 60);
		return NLS.bind(format, Utils.getShortObjectId(commit), message);
	}

