			RepositoryTreeNode<?> node = (RepositoryTreeNode<?>) element;
			RepositoryTreeNodeType type = node.getType();
			switch (type) {
			case REPOGROUP:
				return -1; // On top
			case BRANCHHIERARCHY:
