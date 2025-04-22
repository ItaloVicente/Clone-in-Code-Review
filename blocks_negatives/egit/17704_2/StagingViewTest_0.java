	private void selectRepositoryNode() throws Exception {
		SWTBotTreeItem repoNode = repoViewUtil.getRootItem(repoViewTree,
				repositoryFile);
		repoNode.select();
	}

