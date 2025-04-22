		Repository repo = null;
		if (repositoryMapping != null)
			repo = repositoryMapping.getRepository();
		else if (input instanceof Repository)
			repo = (Repository) input;

