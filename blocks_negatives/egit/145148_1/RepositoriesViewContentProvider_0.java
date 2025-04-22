			List<RepositoryTreeNode> children = new ArrayList<>();
			for (IPath path : hierNode.getChildPaths()) {
				children.add(new BranchHierarchyNode(parentNode, repo, path));
			}
			for (Ref ref : hierNode.getChildRefs()) {
				children.add(new RefNode(parentNode, repo, ref));
			}
			return children.toArray();
