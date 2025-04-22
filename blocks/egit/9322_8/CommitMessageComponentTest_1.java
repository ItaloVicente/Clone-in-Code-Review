	@Test
	public void commitMessageProvider_noProvider() throws Exception {
		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				createProviderList());

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals("", calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_oneProvider() throws Exception {
		String message = "example single-line commit message";

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				createProviderList(message));

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals(message, calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_twoProviders() throws Exception {
		String message1 = "example single-line commit message";
		String message2 = "example multi-line\n\ncommit message";

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				createProviderList(message1, message2));

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals(message1 + "\n\n" + message2, calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_oneCrashingProvider() throws Exception {

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				Arrays.asList(new CrashingCommitMessageProvider()));

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals("", calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_twoProvidersOneCrashing()
			throws Exception {
		String message = "example single-line commit message";
		List<ICommitMessageProvider> providers = createProviderList(message);
		providers.add(new CrashingCommitMessageProvider());

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				providers);

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals(message, calculatedCommitMessage);
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
		}

		return providerList;
	}

	private static class CrashingCommitMessageProvider
			implements ICommitMessageProvider {

		@Override
		public String getMessage(IResource[] resources) {
			throw new RuntimeException();
		}

	}

