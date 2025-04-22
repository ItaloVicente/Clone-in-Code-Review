		if (!repo.getRepositoryState().canCheckout()) {
			MessageDialog.openError(getShell(event),
					UIText.TagAction_cannotCheckout, NLS.bind(
							UIText.TagAction_repositoryState, repo
									.getRepositoryState().getDescription()));
			return null;
		}
