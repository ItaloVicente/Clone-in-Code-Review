	@Test
	public void validateMenuEntriesForMultiSelectionWithMultipleRepositories()
			throws Exception {
		File gitOne = createProjectAndCommitToRepository(REPO1, PROJ1);
		File gitTwo = createProjectAndCommitToRepository(REPO2, PROJ2);

		Repository repoOne = lookupRepository(gitOne);
		Repository repoTwo = lookupRepository(gitTwo);
		for (int i = 0; i < SwitchToMenu.MAX_NUM_MENU_ENTRIES; i++) {
			createBranch(repoOne, "refs/heads/change/" + i);
			createBranch(repoTwo, "refs/heads/change/" + (i + 15));
		}

		mockMultiProjectSelection(PROJ1, PROJ2);

		MenuItem[] items = fillMenu();
		assertTextEquals("change/15", items[0]);
		assertTextEquals("change/16", items[1]);
		assertTextEquals("change/17", items[2]);
		assertTextEquals("change/18", items[3]);
		assertTextEquals("change/19", items[4]);
		assertTextEquals("master", items[5]);
		assertTextEquals("stable", items[6]);
	}

	@Test
	public void validateBranchSwitchingForForMultiSelectionWithMultipleRepositories()
			throws Exception {

		File gitOne = createProjectAndCommitToRepository(REPO1, PROJ1);
		File gitTwo = createProjectAndCommitToRepository(REPO2, PROJ2);
		Repository repoOne = lookupRepository(gitOne);
		Repository repoTwo = lookupRepository(gitTwo);

		try (Git git = new Git(repoOne)) {
			git.checkout().setName("master").call();
		}

		try (Git git = new Git(repoTwo)) {
			git.checkout().setName("stable").call();
		}

		String branchName = "commonBranchAmongRepositories";
		String branchRef = "refs/heads/" + branchName;
		createBranch(repoOne, branchRef);
		createBranch(repoTwo, branchRef);

		assertEquals("master", repoOne.getBranch());
		assertEquals("stable", repoTwo.getBranch());

		SWTBotTree tree = TestUtil.getExplorerTree();
		SWTBotTree select = tree.select(tree.getAllItems());
		ContextMenuHelper.clickContextMenu(select, TEAM_LABEL,
				SWITCH_TO_LABEL_MULTIPLE,
				branchName);
		TestUtil.joinJobs(JobFamilies.CHECKOUT);

		assertEquals(branchName, repoOne.getBranch());
		assertEquals(branchName, repoTwo.getBranch());
	}

	@Test
	public void multipleSelectionWithMultipleRepositoriesAndNoCommonBranches()
			throws Exception {
		File gitOne = createProjectAndCommitToRepository(REPO1, PROJ1);
		File gitTwo = createProjectAndCommitToRepository(REPO2, PROJ2);

		try (Git git = new Git(lookupRepository(gitOne))) {
			git.checkout().setName("stable").call();
			git.branchDelete().setBranchNames("master").setForce(true).call();
		}

		try (Git git = new Git(lookupRepository(gitTwo))) {
			git.branchDelete().setBranchNames("stable").setForce(true).call();
		}

		mockMultiProjectSelection(PROJ1, PROJ2);

		MenuItem[] items = fillMenu();
		assertTextEquals(UIText.SwitchToMenu_NoCommonBranchesFound, items[0]);

		new File(gitOne, Constants.LOGS + "/" + Constants.HEAD).delete();
		new File(gitTwo, Constants.LOGS + "/" + Constants.HEAD).delete();
	}

