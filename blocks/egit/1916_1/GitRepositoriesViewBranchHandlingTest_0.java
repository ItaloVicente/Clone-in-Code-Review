				.getPluginLocalizedValue("RepoViewDeleteBranch.label"));
		refreshAndWait();
		localItem = myRepoViewUtil.getLocalBranchesItem(view.bot().tree(),
				repositoryFile);
		localItem.expand();
		assertEquals("Wrong number of children", 1, localItem.getNodes().size());
	}
	
	@Test
	public void testCreateDeleteLocalBranchWithUnmerged() throws Exception {
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				repositoryFile);
		shareProjects(repositoryFile);
		refreshAndWait();
		final SWTBotView view = getOrOpenView();
		SWTBotTreeItem localItem = myRepoViewUtil.getLocalBranchesItem(view
				.bot().tree(), repositoryFile);
		localItem.expand();
		assertEquals("Wrong number of children", 1, localItem.getNodes().size());

		assertEquals("master", localItem.getNodes().get(0));
		localItem.getNode(0).select();

		ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
				.getPluginLocalizedValue("CreateBranchCommand"));

		SWTBotShell createPage = bot
				.shell(UIText.CreateBranchWizard_NewBranchTitle);
		createPage.activate();
		createPage.bot().textWithId("BranchName").setText("newLocal");
		createPage.bot().checkBox(UIText.CreateBranchPage_CheckoutButton)
				.select();
		createPage.bot().button(IDialogConstants.FINISH_LABEL).click();
		getOrOpenView().toolbarButton("Refresh").click();
		refreshAndWait();

		localItem = myRepoViewUtil.getLocalBranchesItem(view.bot().tree(),
				repositoryFile);
		localItem.expand();
		assertEquals("Wrong number of children", 2, localItem.getNodes().size());
		
		touchAndSubmit("Some more changes");

		localItem.getNode(0).select();
		try {
			ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
		} catch (WidgetNotFoundException e1) {
		}
		localItem.getNode(1).select();
		ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
				.getPluginLocalizedValue("CheckoutCommand"));
		TestUtil.joinJobs(JobFamilies.CHECKOUT);

		try {
			ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
		} catch (WidgetNotFoundException e) {
		}

		localItem.getNode(0).select();
		ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
				.getPluginLocalizedValue("CheckoutCommand"));
		TestUtil.joinJobs(JobFamilies.CHECKOUT);
		localItem.getNode(1).select();
		refreshAndWait();
		ContextMenuHelper.clickContextMenu(bot.tree(), myUtil
				.getPluginLocalizedValue("RepoViewDeleteBranch.label"));
