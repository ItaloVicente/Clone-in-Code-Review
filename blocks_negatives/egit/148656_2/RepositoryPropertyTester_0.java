		GitFlowRepository gitFlowRepository = new GitFlowRepository(repository);
		try {
			if (IS_INITIALIZED.equals(property)) {
				return gitFlowRepository.getConfig().isInitialized();
			} else if (IS_FEATURE.equals(property)) {
				return gitFlowRepository.isFeature();
