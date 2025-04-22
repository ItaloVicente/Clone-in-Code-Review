	private Repository getRepository() {
		if (repositoryMapping != null)
			return repositoryMapping.getRepository();
		else if (input instanceof Repository)
			return (Repository) input;

		return null;
	}

