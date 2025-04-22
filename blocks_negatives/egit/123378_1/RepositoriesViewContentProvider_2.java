			BranchHierarchyNode hierNode = (BranchHierarchyNode) node;
			List<RepositoryTreeNode> children = new ArrayList<>();
			try {
				for (IPath path : hierNode.getChildPaths()) {
					children.add(new BranchHierarchyNode(node, node
							.getRepository(), path));
				}
				for (Ref ref : hierNode.getChildRefs()) {
					children.add(new RefNode(node, node.getRepository(), ref));
				}
			} catch (IOException e) {
				return handleException(e, node);
			}
			return children.toArray();
