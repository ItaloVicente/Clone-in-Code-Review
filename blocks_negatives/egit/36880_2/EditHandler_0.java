		final IStructuredSelection selected = new StructuredSelection(
				new RepositoryCommit(repository, commit));
		CommonUtils.runCommand(
				org.eclipse.egit.ui.internal.commit.command.EditHandler.ID,
				selected);
		openStagingAndRebaseInteractiveViews(repository);
