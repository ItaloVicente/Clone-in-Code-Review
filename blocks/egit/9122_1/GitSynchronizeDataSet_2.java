	public GitSynchronizeData getData(Repository repo) {
		for (GitSynchronizeData gsd : gsdSet)
			if (repo.equals(gsd.getRepository()))
				return gsd;
		return null;
	}

