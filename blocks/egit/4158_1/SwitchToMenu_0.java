		Repository repository = null;
		if (selected instanceof RepositoryNode)
			repository = ((RepositoryNode) selected).getRepository();
		else if (selected instanceof BranchesNode)
			repository = ((BranchesNode) selected).getRepository();
		else if (selected instanceof LocalNode)
			repository = ((LocalNode) selected).getRepository();
		else if ((selected instanceof IProject)) {
			RepositoryMapping mapping = RepositoryMapping
					.getMapping((IProject) selected);
			if (mapping != null)
				repository = mapping.getRepository();
		}

		if (repository != null)
			createDynamicMenu(menu, repository);
	}

	private void createDynamicMenu(Menu menu, final Repository repository) {
