		return null;
	}

	private RepositoryTreeNode getNodeForPath(Repository repository,
			String repoRelativePath) {
		RepositoryTreeNode currentNode = getRepositoryChildNode(repository,
				RepositoryTreeNodeType.WORKINGDIR);
