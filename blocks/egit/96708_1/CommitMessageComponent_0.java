			List<ICommitMessageProvider> messageProviders = getCommitMessageProvider();
			for (ICommitMessageProvider messageProvider : messageProviders) {
				if (messageProvider != null) {
					IResource[] resourcesArray = resources
							.toArray(new IResource[0]);
					calculatedCommitMessage = calculatedCommitMessage
							+ System.getProperty("line.separator") //$NON-NLS-1$
							+ (messageProvider.getMessage(resourcesArray)
									.trim());
				}
