			if (node instanceof RepositoryGroupNode) {
				RepositoryTreeNode candidate = findRepositoryNode(cp,
						cp.getChildren(node), repository);
				if (candidate != null) {
					return candidate;
				}
			} else {
				if (repository.getDirectory()
						.equals(((Repository) node.getObject())
								.getDirectory())) {
					return node;
				}
