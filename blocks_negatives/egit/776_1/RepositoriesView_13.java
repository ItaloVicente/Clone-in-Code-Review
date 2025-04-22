	@SuppressWarnings("boxing")
	private boolean confirmProjectDeletion(List<IProject> projectsToDelete) {
		boolean confirmed;
		confirmed = MessageDialog
				.openConfirm(
						getSite().getShell(),
						UIText.RepositoriesView_ConfirmProjectDeletion_WindowTitle,
						NLS
								.bind(
										UIText.RepositoriesView_ConfirmProjectDeletion_Question,
										projectsToDelete.size()));
		return confirmed;
	}
