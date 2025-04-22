	private boolean hasBranches(Repository repository) {
		try {
			return !repository.getRefDatabase().getRefs(Constants.R_HEADS)
					.isEmpty();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
			return false;
		}
	}

