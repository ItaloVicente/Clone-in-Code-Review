	@Test
	public void commitMessageProvider_twoProvidersFirstOneCrashing()
			throws Exception {
		String message = "example single-line commit message";
		List<ICommitMessageProvider> providers = createProviderList(message);
		providers.add(0, new CrashingCommitMessageProvider());

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				providers);
