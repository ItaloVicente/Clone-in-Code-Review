		Repository repo = getRepository();
		if (repo == null)
			return false;
		try {
			String fullBranch = repo.getFullBranch();
			return (fullBranch.startsWith(Constants.R_REFS));
		} catch (IOException e) {
			return false;
