			ExecutionEvent event) throws OperationCanceledException {

		String message = NLS.bind(
				UIText.RepositoriesView_ConfirmProjectDeletion_Question,
				projectsToDelete.size());
		MessageDialog dlg = new MessageDialog(getView(event).getSite()
				.getShell(),
				UIText.RepositoriesView_ConfirmProjectDeletion_WindowTitle,
				null, message, MessageDialog.INFORMATION, new String[] {
						IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
						IDialogConstants.CANCEL_LABEL }, 0);
		int index = dlg.open();
		if (index == 2)
			throw new OperationCanceledException();

		return index == 0;
