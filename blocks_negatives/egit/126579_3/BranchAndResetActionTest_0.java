		RepositoriesViewLabelProvider provider = GitRepositoriesViewTestUtils
				.createLabelProvider();
		LOCAL_BRANCHES = provider.getText(new LocalNode(new RepositoryNode(
				null, repo), repo));
		TAGS = provider.getText(new TagsNode(new RepositoryNode(null, repo),
				repo));
