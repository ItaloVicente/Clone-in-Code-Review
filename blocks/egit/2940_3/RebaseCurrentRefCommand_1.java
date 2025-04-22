		final Repository repository;
		if (selected instanceof IProject) {
			ref = null;
			IProject project = (IProject) selected;
			repository = RepositoryMapping.getMapping(project).getRepository();
		} else {
			RepositoryTreeNode node = (RepositoryTreeNode) selected;
			repository = node.getRepository();
