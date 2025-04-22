			String contributorName = "<unknown"; //$NON-NLS-1$
			try {
				contributorName = config.getDeclaringExtension()
						.getContributor().getName();
				provider = config.createExecutableExtension("class");//$NON-NLS-1$
				if (provider instanceof ICommitMessageProvider) {
					providers.add((ICommitMessageProvider) provider);
				} else {
					Activator.logError(
							UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
							null);
				}
			} catch (CoreException | InvalidRegistryObjectException e) {
