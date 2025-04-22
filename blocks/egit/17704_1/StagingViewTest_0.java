	private void selectRepositoryNode() throws Exception {
		SWTBotView repositoriesView = TestUtil
				.showView(RepositoriesView.VIEW_ID);
		SWTBotTree tree = repositoriesView.bot().tree();

		SWTBotTreeItem repoNode = repoViewUtil
				.getRootItem(tree, repositoryFile);
		repoNode.select();
	}
