		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configs = registry
				.getConfigurationElementsFor(COMMIT_MESSAGE_PROVIDER_ID);
		for (IConfigurationElement config : configs) {
			Object provider;
			try {
				extensionId = config.getDeclaringExtension()
						.getUniqueIdentifier();
				contributorName = config.getContributor().getName();
				if (provider instanceof ICommitMessageProvider) {
					providers.add((ICommitMessageProvider) provider);
				} else {
					Activator.logError(MessageFormat.format(
							UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
							extensionId, contributorName), null);
				}
			} catch (CoreException | RuntimeException e) {
				Activator.logError(
						MessageFormat.format(
								UIText.CommitDialog_ErrorCreatingCommitMessageProvider,
								extensionId, contributorName),
						e);
			}
		}
		return providers;
