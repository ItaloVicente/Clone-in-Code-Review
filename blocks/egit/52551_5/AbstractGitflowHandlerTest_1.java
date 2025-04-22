		CommitCommand commit = git.commit().setMessage(newContent);
		commit.setAuthor(TestUtil.TESTCOMMITTER_NAME, TestUtil.TESTCOMMITTER_EMAIL);
		commit.setCommitter(TestUtil.TESTCOMMITTER_NAME, TestUtil.TESTCOMMITTER_EMAIL);
		return commit.call();
	}

	protected void createFeature(String featureName) throws CoreException {
		new FeatureStartOperation(new GitFlowRepository(repository),
				featureName).execute(null);
	}

	protected void checkoutFeature(String featureName) throws CoreException {
		new FeatureCheckoutOperation(new GitFlowRepository(repository),
				featureName).execute(null);
