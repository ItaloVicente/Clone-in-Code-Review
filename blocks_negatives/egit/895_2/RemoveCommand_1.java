			ExecutionEvent event) {
		boolean confirmed;
		confirmed = MessageDialog
				.openConfirm(
						getView(event).getSite().getShell(),
						UIText.RepositoriesView_ConfirmProjectDeletion_WindowTitle,
						NLS
								.bind(
										UIText.RepositoriesView_ConfirmProjectDeletion_Question,
										projectsToDelete.size()));
		return confirmed;
