	private void performTaskAfterCommit(RevCommit commit) {
		if (commit != null) {
			ICommitMessageProvider commitProvider;
			try {
				commitProvider = getCommitMessageProvider();
				if (commitProvider != null)
					commitProvider.performTaskAfterCommit(new RepositoryCommit(
							repository, commit));
			} catch (CoreException e) {
			}
		}
	}

	private ICommitMessageProvider getCommitMessageProvider()
			throws CoreException {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(COMMIT_MESSAGE_PROVIDER_ID);
		if (config.length > 0) {
			Object provider;
			for (IConfigurationElement configElement : config) {
				provider = configElement.createExecutableExtension("class");//$NON-NLS-1$
				if (provider instanceof ICommitMessageProvider) {
					return (ICommitMessageProvider) provider;
				} else {
					Activator
							.logError(
									UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
									null);
				}
			}
		}
		return null;
	}

