
	private Ref getBranchRef(Repository repository) {
		try {
			String fullBranch = repository.getFullBranch();
			if (fullBranch != null && fullBranch.startsWith(Constants.R_HEADS))
				return repository.exactRef(fullBranch);
		} catch (IOException e) {
			Activator.handleError(e.getLocalizedMessage(), e, false);
		}
		return null;
	}
