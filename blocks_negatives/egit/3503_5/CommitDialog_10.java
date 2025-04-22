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
