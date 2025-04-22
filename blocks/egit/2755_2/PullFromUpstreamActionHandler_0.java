		Repository[] repos = getRepositories();
		for (Repository repo : repos) {
			if (repo == null)
				return false;
			try {
				String fullBranch = repo.getFullBranch();
				if (!fullBranch.startsWith(Constants.R_REFS))
					return false;
			} catch (IOException e) {
				return false;
			}
