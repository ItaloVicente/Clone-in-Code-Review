		Git repoTree;
		for (IResource res : resources) {
			repoTree = new Git(getRepository(res));
			repoTree.clean().call();
		}
	}

	public Set<String> dryRun() {
		if (resources.length == 0)
			return null;

		Repository repo = getRepository(resources[0]);
		CleanCommand clean = new Git(repo).clean();

		return clean.setDryRun(true).call();
	}

	private static Repository getRepository(IResource resource) {
		RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(resource.getProject());
		if (repositoryMapping != null)
			return repositoryMapping.getRepository();
		else
			return null;
