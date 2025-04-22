
	private boolean hasStashedCommits(final Repository repository) {
		try {
			return repository.getRef(Constants.R_STASH) != null;
		} catch (IOException e) {
			return false;
		}
	}
