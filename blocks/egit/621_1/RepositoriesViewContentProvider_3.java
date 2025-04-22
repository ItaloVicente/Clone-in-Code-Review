
			List<RepositoryTreeNode<String>> nodes = new ArrayList<RepositoryTreeNode<String>>();

			nodes.add(new RepositoryTreeNode<String>(node,
					RepositoryTreeNodeType.LOCALBRANCHES, repo, "")); //$NON-NLS-1$
			nodes.add(new RepositoryTreeNode<String>(node,
					RepositoryTreeNodeType.REMOTEBRANCHES, repo, "")); //$NON-NLS-1$

			return nodes.toArray();
		}

		case LOCALBRANCHES: {
