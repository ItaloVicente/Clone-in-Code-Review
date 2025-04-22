		Repository[] repos = getRepositories();
		for (Repository repo : repos) {
			try {
				String fullBranch = repo.getFullBranch();
				if (!fullBranch.startsWith(Constants.R_REFS)
						|| repo.getAllRefs().isEmpty())
					return false;
			} catch (IOException e) {
				return false;
			}
