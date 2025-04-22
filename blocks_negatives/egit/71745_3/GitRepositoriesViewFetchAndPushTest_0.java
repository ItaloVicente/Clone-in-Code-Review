		if (useRemote)
			myRepoViewUtil.getRemotesItem(tree, clonedRepositoryFile).expand()
					.getNode("origin").select();
		else
			myRepoViewUtil.getRemotesItem(tree, clonedRepositoryFile).expand()
					.getNode("origin").expand().getNode(fetchMode ? 0 : 1)
