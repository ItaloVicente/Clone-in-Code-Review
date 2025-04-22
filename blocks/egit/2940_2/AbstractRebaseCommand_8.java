		final Repository repository;
		if (selected instanceof IProject) {
			IProject project = (IProject) selected;
			repository = RepositoryMapping.getMapping(project).getRepository();
		} else {
			RepositoryTreeNode node = (RepositoryTreeNode) selected;
			repository = node.getRepository();
		}
