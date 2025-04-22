			if (branchHierarchyMode) {
				BranchHierarchyNode hierNode = new BranchHierarchyNode(node,
						repo, new Path(Constants.R_HEADS));
				List<RepositoryTreeNode> children = new ArrayList<RepositoryTreeNode>();
				try {
					for (IPath path : hierNode.getChildPaths()) {
						children.add(new BranchHierarchyNode(node, node
								.getRepository(), path));
					}
					for (Ref ref : hierNode.getChildRefs()) {
						children.add(new RefNode(node, node.getRepository(),
								ref));
					}
				} catch (IOException e) {
					return handleException(e, node);
