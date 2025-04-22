		if (target != null) {
			if (!repository.getRepositoryState().canCheckout()) {
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openError(getShell(),
										UIText.BranchAction_cannotCheckout,
										NLS.bind(
												UIText.BranchAction_repositoryState,
												repository.getRepositoryState()
														.getDescription()));
							}
						});
				return null;
			}
