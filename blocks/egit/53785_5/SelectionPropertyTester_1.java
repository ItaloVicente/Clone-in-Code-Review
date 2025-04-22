	private boolean haveRepositories(IResource[] resources) {
		for (IResource resource : resources) {
			Repository r = getRepositoryOfMapping(resource);
			if (r == null) {
				return false;
			}
		}
		return true;
	}

