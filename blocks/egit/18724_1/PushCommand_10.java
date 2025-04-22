		List<RepositoryTreeNode> nodes = getSelectedNodes();
		if (nodes.isEmpty())
			return false;
		Repository repository = nodes.get(0).getRepository();
		for (RepositoryTreeNode node : nodes) {
			if (repository != node.getRepository())
				return false;
		}
