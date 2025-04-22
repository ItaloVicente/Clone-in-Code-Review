	@Test
	public void testNoHeadSimplePushDisabled() throws Exception {
		Repository emptyRepo = createLocalTestRepository("empty");
		File gitDir = emptyRepo.getDirectory();
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(gitDir);
		GitRepositoriesViewTestUtils viewUtil = new GitRepositoriesViewTestUtils();
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem repoItem = viewUtil.getRootItem(tree, gitDir);
		repoItem.select();
		boolean enabled = ContextMenuHelper.isContextMenuItemEnabled(tree,
				NLS.bind(UIText.PushMenu_PushBranch, "master"));
		assertFalse("Push branch should be disabled if there is no HEAD",
				enabled);
		enabled = ContextMenuHelper.isContextMenuItemEnabled(tree,
				util.getPluginLocalizedValue("PushToUpstreamCommand.label"));
		assertFalse("Push to upstream should be disabled if there is no HEAD",
				enabled);
	}

