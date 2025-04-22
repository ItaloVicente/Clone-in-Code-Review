	@Test
	public void commitMessageProvider_twoProvidersSecondOneCrashing()
			throws Exception {
		String message = "example single-line commit message";
		List<ICommitMessageProvider> providers = createProviderList(message);
		providers.add(new CrashingCommitMessageProvider());
