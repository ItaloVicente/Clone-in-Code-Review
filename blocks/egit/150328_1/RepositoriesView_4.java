			if (node instanceof RepositoryGroupNode) {
				RepositoryTreeNode candidate = findRepositoryNode(cp,
						cp.getChildren(node), repository);
				if (candidate != null) {
					return candidate;
				}
			}
