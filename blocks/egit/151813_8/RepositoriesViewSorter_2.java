			RepositoryTreeNode<?> node = (RepositoryTreeNode<?>) element;
			RepositoryTreeNodeType type = node.getType();
			switch (type) {
			case REPOGROUP:
				return RepositoryTreeNodeType.values().length; // At the bottom
			case BRANCHHIERARCHY:
