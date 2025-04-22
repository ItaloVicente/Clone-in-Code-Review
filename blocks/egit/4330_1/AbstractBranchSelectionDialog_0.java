				final RepositoryTreeNodeType type = node.getType();
				if (type != RepositoryTreeNodeType.REF
						&& type != RepositoryTreeNodeType.TAG
						&& type != RepositoryTreeNodeType.ADDITIONALREF)
					branchTree.setExpandedState(node,
							!branchTree.getExpandedState(node));
