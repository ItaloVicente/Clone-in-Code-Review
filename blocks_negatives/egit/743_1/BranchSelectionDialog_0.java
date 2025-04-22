		localBranches = new RepositoryTreeNode<Repository>(null,
				RepositoryTreeNodeType.LOCALBRANCHES, this.repo, this.repo);
		remoteBranches = new RepositoryTreeNode<Repository>(null,
				RepositoryTreeNodeType.REMOTEBRANCHES, this.repo, this.repo);
		tags = new RepositoryTreeNode<Repository>(null,
				RepositoryTreeNodeType.TAGS, this.repo, this.repo);
