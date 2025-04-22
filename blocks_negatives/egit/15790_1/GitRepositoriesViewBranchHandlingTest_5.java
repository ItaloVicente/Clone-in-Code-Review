		SWTBotPerspective perspective = null;
		try {
			perspective = bot.activePerspective();
			bot.perspectiveById("org.eclipse.pde.ui.PDEPerspective").activate();
			clearView();
			Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
					clonedRepositoryFile);
			shareProjects(clonedRepositoryFile);
			refreshAndWait();

			Repository repo = lookupRepository(clonedRepositoryFile);
			BranchOperation bop = new BranchOperation(repo, "refs/heads/master");
			bop.execute(null);

			assertEquals("master", repo.getBranch());
			SWTBotTree tree = getOrOpenView().bot().tree();

			SWTBotTreeItem item = myRepoViewUtil.getLocalBranchesItem(tree,
					clonedRepositoryFile).expand();

			touchAndSubmit(null);
			refreshAndWait();

			item = myRepoViewUtil.getRemoteBranchesItem(tree,
					clonedRepositoryFile).expand();
			List<String> children = item.getNodes();
			assertEquals("Wrong number of remote children", 2, children.size());

			item.getNode("origin/stable").select();
			ContextMenuHelper.clickContextMenuSync(tree, myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
			TestUtil.joinJobs(JobFamilies.CHECKOUT);
			refreshAndWait();

			GitLightweightDecorator.refresh();

			assertTrue("Branch should not be symbolic", ObjectId
					.isId(lookupRepository(clonedRepositoryFile).getBranch()));

			item = myRepoViewUtil.getRemoteBranchesItem(tree,
					clonedRepositoryFile).expand();
			item.getNode("origin/stable").select();
			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("CreateBranchCommand"));

			SWTBotShell createPage = bot
					.shell(UIText.CreateBranchWizard_NewBranchTitle);
			createPage.activate();
			assertEquals("Wrong suggested branch name", "stable", createPage
					.bot().textWithId("BranchName").getText());
			createPage.close();

			myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile)
					.expand().getNode("master").select();
			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
			TestUtil.joinJobs(JobFamilies.CHECKOUT);
			refreshAndWait();

		} finally {
			if (perspective != null)
				perspective.activate();
		}
