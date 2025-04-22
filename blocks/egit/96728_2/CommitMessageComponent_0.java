			try {
				provider = config.createExecutableExtension("class");//$NON-NLS-1$
				if (provider instanceof ICommitMessageProvider) {
					providers.add((ICommitMessageProvider) provider);
				} else {
					Activator.logError(
							UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
							null);
				}
			} catch (CoreException | InvalidRegistryObjectException e) {
				String contributorName;
				try {
					contributorName = config.getDeclaringExtension()
							.getContributor().getName();
				} catch (InvalidRegistryObjectException e1) {
					contributorName = ""; //$NON-NLS-1$
				}
