		Git git = new Git(repository);
		try {
			Status status = git.status().call();
			return status.getModified().isEmpty()
					&& status.getUntracked().isEmpty()
					&& status.getMissing().isEmpty();
		} catch (Exception e) {
			MessageDialog.openError(getShell(),
					UIText.GitCreatePatchAction_cannotCreatePatch, e
							.getMessage() == null ? e.getMessage()
							: UIText.GitCreatePatchWizard_InternalError);
