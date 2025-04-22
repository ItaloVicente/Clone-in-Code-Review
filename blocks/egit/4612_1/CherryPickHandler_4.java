		if (repo == null)
			return null;

		final IStructuredSelection selected = new StructuredSelection(
				new RepositoryCommit(repo, commit));
		CommonUtils
				.runCommand(
						org.eclipse.egit.ui.internal.commit.command.CherryPickHandler.ID,
						selected);
