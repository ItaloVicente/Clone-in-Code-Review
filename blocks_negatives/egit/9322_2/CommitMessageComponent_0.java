			ICommitMessageProvider messageProvider = getCommitMessageProvider();
			if (messageProvider != null) {
				IResource[] resourcesArray = resources
						.toArray(new IResource[0]);
				calculatedCommitMessage = messageProvider
						.getMessage(resourcesArray);
