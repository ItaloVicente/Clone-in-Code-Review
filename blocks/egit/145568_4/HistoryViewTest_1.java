	@Test
	public void testSelectBranch() throws Exception {
		toggleShowAllBranchesButton(false);
		SWTBotTable commitTable = getHistoryViewTable(PROJ1);
		assertEquals("Unexpected number of commits", commitCount,
				commitTable.rowCount());
		try (Git git = new Git(lookupRepository(repoFile))) {
			git.checkout().setCreateBranch(true).setName("otherBranch").call();
			TestUtil.waitForDecorations();
			touchAndSubmit("Updated");
			ObjectId otherCommit = git.getRepository().resolve("otherBranch");
			Ref master = git.checkout().setName(Constants.MASTER).call();
			assertNotNull("Branch is null", master.getLeaf().getObjectId());
			assertNotEquals("Branch not switched", otherCommit,
					master.getLeaf().getObjectId());
			TestUtil.waitForDecorations();
			commitTable = getHistoryViewTable();
			assertEquals("Unexpected number of commits", commitCount,
					commitTable.rowCount());
			SWTBotView view = TestUtil.showView(RepositoriesView.VIEW_ID);
			TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
			TestUtil.waitForDecorations();
			SWTBotTree tree = view.bot().tree();
			SWTBotTreeItem localBranches = myRepoViewUtil
					.getLocalBranchesItem(tree, repoFile);
			TestUtil.expandAndWait(localBranches).getNode("otherBranch")
					.select();
			ContextMenuHelper.clickContextMenuSync(tree, "Show In", "History");
			commitTable = getHistoryViewTable();
			assertEquals("Unexpected number of commits", commitCount + 1,
					commitTable.rowCount());
			Table swtTable = commitTable.widget;
			ObjectId[] firstId = { null };
			swtTable.getDisplay().syncExec(() -> {
				Object obj = swtTable.getItem(0).getData();
				RevCommit c = Adapters.adapt(obj, RevCommit.class);
				if (c != null) {
					firstId[0] = c.getId();
				}
			});
			assertEquals("Unexpected commit in table", otherCommit, firstId[0]);
		}
	}

