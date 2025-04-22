		return parent;
	}

	@Override
	public void create() {
		super.create();

		List<RepositoryTreeNode> roots = new ArrayList<RepositoryTreeNode>();
		roots.add(localBranches);
		roots.add(remoteBranches);
		roots.add(tags);

		branchTree.setInput(roots);

