	private void createFeature(String featureName) throws CoreException {
		new FeatureStartOperation(new GitFlowRepository(repository),
				featureName).execute(null);
	}

	private void checkoutFeature(String featureName) throws CoreException {
