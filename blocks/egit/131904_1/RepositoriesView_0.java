		return null;
	}

	private RepositoryTreeNode getNodeForPath(Repository repository,
			String repoRelativePath) {
		RepositoryTreeNode workingDirNode = getRepositoryChildNode(repository,
				RepositoryTreeNodeType.WORKINGDIR);
		RepositoryTreeNode currentNode = workingDirNode;
