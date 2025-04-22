		return parent;
	}

	@Override
	public void create() {
		super.create();

		List<RepositoryTreeNode> roots = new ArrayList<RepositoryTreeNode>();
		roots.add(new RepositoryTreeNode<Repository>(null,
				RepositoryTreeNodeType.LOCALBRANCHES, repo, repo));
		roots.add(new RepositoryTreeNode<Repository>(null,
				RepositoryTreeNodeType.REMOTEBRANCHES, repo, repo));
		roots.add(new RepositoryTreeNode<Repository>(null,
				RepositoryTreeNodeType.TAGS, repo, repo));

		branchTree.setInput(roots);
		branchTree.expandAll();

