	private boolean existsLocalBranch(String suggestedBranchName) {
		ObjectId localBranch = null;
		try {
			localBranch = myRepository.resolve(Constants.R_HEADS
					+ suggestedBranchName);
		} catch (Exception e) {
			Activator.handleError(UIText.CreateBranchWizard_CreationFailed,
					e.getCause(), false);
		}
		return localBranch != null;
	}

