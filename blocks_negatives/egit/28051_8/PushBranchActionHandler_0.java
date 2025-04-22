	@Override
	public boolean isEnabled() {
		Repository repository = getRepository();
		if (repository == null)
			return false;
		return getBranchRef(repository) != null;
	}

