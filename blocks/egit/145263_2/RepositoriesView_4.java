				childNode = findChild(cp, node, type);
			} else {
				RepositoryTreeNode submodules = findChild(cp, node,
						RepositoryTreeNodeType.SUBMODULES);
				if (submodules != null) {
					childNode = recursiveRepositoryChildNode(cp, submodules,
							repository, type);
