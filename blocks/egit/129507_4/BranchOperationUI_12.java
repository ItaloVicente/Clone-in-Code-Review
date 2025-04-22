					new NonDeletedFilesDialog(shell, repository,
							result.getUndeletedList()).open();
			});
		} else {

			String repoName = Activator.getDefault().getRepositoryUtil()
					.getRepositoryName(repository);
			String message = NLS.bind(
					UIText.BranchOperationUI_CheckoutError_DialogMessage,
					repoName, target);
			PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(shell,
						UIText.BranchOperationUI_CheckoutError_DialogTitle,
						message);
