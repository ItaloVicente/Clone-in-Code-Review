		TestUtil.waitForDecorations();
	}

	@Override
	protected void clearAllConfiguredRepositories() throws Exception {
		super.clearAllConfiguredRepositories();
		refreshAndWait();
