	protected String calculateCommitMessage() {
		String calculatedCommitMessage = commitMessage;

		Set<IResource> resources = new HashSet<IResource>();
		for (CommitItem item : items) {
			IResource resource = item.file.getProject();
			resources.add(resource);
		}

		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(COMMIT_MESSAGE_PROVIDER_ID);
		for (IConfigurationElement e : config) {
			Object provider;
			try {
				provider = e.createExecutableExtension("class");//$NON-NLS-1$
				if (provider instanceof ICommitMessageProvider) {
					if (!"".equals(calculatedCommitMessage)){ //$NON-NLS-1$
						calculatedCommitMessage += Text.DELIMITER;
					}
					calculatedCommitMessage += ((ICommitMessageProvider) provider).getMessage(resources.toArray(new IResource[0])) ;
				}
			} catch (CoreException coreException) {
				Activator.error(coreException.getLocalizedMessage(), coreException);
			}
		}
		return calculatedCommitMessage;
	}

