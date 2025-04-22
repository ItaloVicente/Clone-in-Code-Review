	private String calculateCommitMessage() {
		String calculatedCommitMessage = null;

		Set<IResource> resources = new HashSet<IResource>();
		for (CommitItem item : items) {
			IResource resource = item.file.getProject();
			resources.add(resource);
		}
		try {
			ICommitMessageProvider messageProvider = getCommitMessageProvider();
			if(messageProvider != null) {
				IResource[] resourcesArray = resources.toArray(new IResource[0]);
				calculatedCommitMessage = messageProvider.getMessage(resourcesArray);
			}
		} catch (CoreException coreException) {
			Activator.error(coreException.getLocalizedMessage(),
					coreException);
		}
		return calculatedCommitMessage;
	}


	private ICommitMessageProvider getCommitMessageProvider()
			throws CoreException {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(COMMIT_MESSAGE_PROVIDER_ID);
		if (config.length > 0) {
			Object provider;
			provider = config[0].createExecutableExtension("class");//$NON-NLS-1$
			if (provider instanceof ICommitMessageProvider) {
				return (ICommitMessageProvider) provider;
			} else {
				Activator.logError(UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
						null);
			}
		}
		return null;
	}

