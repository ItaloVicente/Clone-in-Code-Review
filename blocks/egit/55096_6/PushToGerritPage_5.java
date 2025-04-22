		try {
			final BranchConfig branchConfig = new BranchConfig(
					repository.getConfig(), repository.getBranch());
			final String trackedBranch = branchConfig.getMerge();
			if (trackedBranch != null) {
				lastBranch = trackedBranch.replace(Constants.R_HEADS, ""); //$NON-NLS-1$
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		if (lastBranch != null) {
