				});
		Set<IResource> resources = new HashSet<IResource>();
		for (CommitItem item : items) {
			IFile file = findFile(item.path);
			if (file != null)
				resources.add(file.getProject());
		}
		if (!amending && commitMessage == null)
			try {
				ICommitMessageProvider messageProvider = getCommitMessageProvider();
				if (messageProvider != null)
					messageSection.setMessage(messageProvider
							.getMessage(resources
									.toArray(new IResource[resources.size()])));
			} catch (CoreException coreException) {
				Activator.error(coreException.getLocalizedMessage(),
						coreException);
