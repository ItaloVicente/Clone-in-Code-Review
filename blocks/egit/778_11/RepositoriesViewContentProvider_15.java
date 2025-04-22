		List<RepositoryTreeNode> nodes = new ArrayList<RepositoryTreeNode>();
		List<String> directories = new ArrayList<String>();

		if (inputElement instanceof Collection) {
			for (Iterator it = ((Collection) inputElement).iterator(); it
					.hasNext();) {
				Object next = it.next();
				if (next instanceof RepositoryTreeNode)
					nodes.add((RepositoryTreeNode) next);
				else if (next instanceof String)
					directories.add((String) next);
			}
		} else if (inputElement instanceof IWorkspaceRoot) {
			directories.addAll(Activator.getDefault().getRepositoryUtil()
					.getConfiguredRepositories());
		}

		for (String directory : directories) {
			try {
				RepositoryNode rNode = new RepositoryNode(null, repositoryCache
						.lookupRepository(new File(directory)));
				nodes.add(rNode);
			} catch (IOException e) {
			}
		}

