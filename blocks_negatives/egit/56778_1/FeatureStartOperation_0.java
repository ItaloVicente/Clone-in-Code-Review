		String branchName = repository.getConfig().getFeatureBranchName(featureName);

		try {
			if (!repository.isDevelop()) {
				throw new CoreException(
						error(NLS.bind(CoreText.FeatureStartOperation_notOn, repository.getConfig().getDevelop())));
			}
		} catch (IOException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
		RevCommit head;
		try {
			head = repository.findHead();
		} catch (WrongGitFlowStateException e) {
			throw new CoreException(error(e));
		}
