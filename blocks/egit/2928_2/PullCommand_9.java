		Set<Repository> repositories = new HashSet<Repository>();
		for (RepositoryNode node : getSelectedNodes(event)) {
			if (node.getRepository() != null)
				repositories.add(node.getRepository());
		}

		if (repositories.isEmpty())
