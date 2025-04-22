		final IStructuredSelection selected = new StructuredSelection(
				new RepositoryCommit(repo, commit));
		CommonUtils
				.runCommand(
						org.eclipse.egit.ui.internal.commit.command.RevertHandler.ID,
						selected);
