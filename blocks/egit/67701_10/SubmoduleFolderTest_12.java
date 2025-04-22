	private boolean containsRepo(Repository[] repositories, Repository needle) {
		for (Repository repo : repositories) {
			if (needle.equals(repo)) {
				return true;
			}
		}
		return false;
	}

