			for (String segment : relPath.segments()) {
				for (Object child : cp.getChildren(currentNode)) {
					RepositoryTreeNode<File> childNode = (RepositoryTreeNode<File>) child;
					if (childNode.getObject().getName().equals(segment)) {
						currentNode = childNode;
						break;
					}
				}
