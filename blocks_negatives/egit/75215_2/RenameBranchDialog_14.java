		String refName = refNameFromDialog();
		String refPrefix;

		if (refName.startsWith(Constants.R_HEADS))
			refPrefix = Constants.R_HEADS;
		else if (refName.startsWith(Constants.R_REMOTES))
			refPrefix = Constants.R_REMOTES;
		else if (refName.startsWith(Constants.R_TAGS))
			refPrefix = Constants.R_TAGS;
		else {
			return;
		}

		String branchName = refName.substring(refPrefix.length());

		InputDialog labelDialog = getRefNameInputDialog(NLS.bind(
				UIText.RenameBranchDialog_NewNameInputDialogPrompt, branchName,
				refPrefix), refPrefix, branchName);
		if (labelDialog.open() == Window.OK) {
			String newRefName = refPrefix + labelDialog.getValue();
			try (Git git = new Git(repo)) {
				git.branchRename().setOldName(refName)
						.setNewName(labelDialog.getValue()).call();
				branchTree.refresh();
				markRef(newRefName);
			} catch (Throwable e1) {
				reportError(e1, UIText.RenameBranchDialog_RenameErrorMessage,
						refName, newRefName, e1.getMessage());
			}
