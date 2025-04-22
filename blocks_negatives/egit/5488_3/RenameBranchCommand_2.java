		String oldName = refNode.getObject().getName();
		String prefix;
		if (oldName.startsWith(Constants.R_HEADS))
			prefix = Constants.R_HEADS;
		else if (oldName.startsWith(Constants.R_REMOTES))
			prefix = Constants.R_REMOTES;
		else
			throw new ExecutionException(NLS.bind(
					UIText.RenameBranchCommand_WrongNameMessage, oldName));
		Repository db = refNode.getRepository();
		IInputValidator inputValidator = ValidationUtils
				.getRefNameInputValidator(db, prefix, true);
		String defaultValue = Repository.shortenRefName(oldName);
		InputDialog newNameDialog = new InputDialog(shell,
				UIText.RepositoriesView_RenameBranchTitle, NLS.bind(
						UIText.RepositoriesView_RenameBranchMessage,
						defaultValue), defaultValue, inputValidator);
		if (newNameDialog.open() == Window.OK) {
			try {
				String newName = newNameDialog.getValue();
				new RenameBranchOperation(db, refNode.getObject(), newName)
						.execute(null);
			} catch (CoreException e) {
				Activator.handleError(
						UIText.RepositoriesView_RenameBranchFailure, e, true);
			}
		}
