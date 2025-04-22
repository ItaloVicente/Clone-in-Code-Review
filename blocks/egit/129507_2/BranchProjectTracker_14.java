
		for (Repository repo : repositories) {
			String branch = getBranch(repo);
			if (branch != null) {
				restore(repo, branch, monitor);
			}
