	public void commitMessageProvider_multipleProvidersWithCrashAndNull()
			throws Exception {
		String singleLineMessage = "example single-line commit message";
		String multiLineMessage = "example\nmulti-line\n\ncommit message";
		List<ICommitMessageProvider> providers = createProviderList(
				multiLineMessage + "\n\n\n", null, "\n" + singleLineMessage);
		providers.add(0, new CrashingCommitMessageProvider());
		providers.add(3, new CrashingCommitMessageProvider());

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				providers);

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals(multiLineMessage + "\n\n" + singleLineMessage,
				calculatedCommitMessage);
	}

	private CommitMessageComponent newCommitMessageComponent(
			List<ICommitMessageProvider> providers) {
		return new CommitMessageComponent(null) {

			@Override
			List<ICommitMessageProvider> getCommitMessageProviders() {
				return providers;
			}
		};
	}

	private List<ICommitMessageProvider> createProviderList(
			String... messages) {
		List<ICommitMessageProvider> providerList = new ArrayList<>();

		for (String message : messages) {
			providerList.add(new ICommitMessageProvider() {

				@Override
				public String getMessage(IResource[] resources) {
					return message;
				}
			});
