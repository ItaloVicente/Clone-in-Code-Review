	static String identify(Repository git) {
		if (git instanceof DfsRepository) {
			return ((DfsRepository) git).getDescription().getRepositoryName();
		} else if (git.getDirectory() != null) {
			return git.getDirectory().getPath();
		}
		return "unknown";
	}

