	/**
	 * @return the calculated commit message
	 */
	private String calculateCommitMessage() {
		if(commitMessage != null) {
			return commitMessage;
		}

		if (amending)
			return previousCommitMessage;
		String calculatedCommitMessage = null;

		Set<IResource> resources = new HashSet<IResource>();
		for (CommitItem item : items) {
			IFile file = findFile(item.path);
			if (file != null)
				resources.add(file.getProject());
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
		if (calculatedCommitMessage != null)
			return calculatedCommitMessage;
		else
	}

