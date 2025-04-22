	private Object[] getBranchChildren(RepositoryTreeNode node, Repository repo,
			String prefix) {
		if (branchHierarchyMode) {
			return getBranchHierarchyChildren(
					new BranchHierarchyNode(node, repo, new Path(prefix)), repo,
					node);
		} else {
			try {
				return getRefs(repo, prefix).values().stream()
						.filter(ref -> !ref.isSymbolic())
						.map(ref -> new RefNode(node, repo, ref)).toArray();
			} catch (IOException e) {
				return handleException(e, node);
			}
		}
	}

	private Object[] getBranchHierarchyChildren(BranchHierarchyNode hierNode,
			Repository repo, RepositoryTreeNode parentNode) {
		try {
			List<RepositoryTreeNode> children = new ArrayList<>();
			for (IPath path : hierNode.getChildPaths()) {
				children.add(new BranchHierarchyNode(parentNode, repo, path));
			}
			for (Ref ref : hierNode.getChildRefs()) {
				children.add(new RefNode(parentNode, repo, ref));
			}
			return children.toArray();
		} catch (IOException e) {
			return handleException(e, parentNode);
		}
	}

