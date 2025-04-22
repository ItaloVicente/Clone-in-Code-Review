	private boolean targetIsCurrentBranch(RepositoryTreeNode element,
			String refName) {
		try {
			Repository repository = element.getRepository();
			return refName.equals(repository.getFullBranch());
		} catch (IOException e) {
			return false;
		}
	}

