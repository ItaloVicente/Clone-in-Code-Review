			final IResource resource) {
		final List<PreviousCommit> previousList;
		try {
			previousList = findPreviousCommits();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
			return null;
		}
