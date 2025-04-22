				return node;
			}
		}
		for (Object repo : roots) {
			RepositoryTreeNode node = (RepositoryTreeNode) repo;
			RepositoryTreeNode submodules = findChild(cp, node,
						RepositoryTreeNodeType.SUBMODULES);
			if (submodules != null) {
				RepositoryTreeNode submoduleNode = findRepositoryNode(cp,
						cp.getChildren(submodules), repository);
				if (submoduleNode != null) {
					return submoduleNode;
