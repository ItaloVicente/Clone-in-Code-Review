		final String[] dialogResult = new String[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				String[] buttons = new String[] {
						UIText.BranchOperationUI_CheckoutRemoteTrackingAsLocal,
						UIText.BranchOperationUI_CheckoutRemoteTrackingCommit,
						IDialogConstants.CANCEL_LABEL };
				MessageDialog questionDialog = new MessageDialog(
						getShell(),
						UIText.BranchOperationUI_CheckoutRemoteTrackingTitle,
						null,
						UIText.BranchOperationUI_CheckoutRemoteTrackingQuestion,
						MessageDialog.QUESTION, buttons, 0);
				int result = questionDialog.open();
				if (result == 0) {
					CreateBranchWizard wizard = new CreateBranchWizard(repository,
							target);
					WizardDialog createBranchDialog = new WizardDialog(getShell(),
							wizard);
					createBranchDialog.open();
					return;
				} else if (result == 1) {
					dialogResult[0] = target;
					return;
				} else {
					return;
				}
			}
		});
		return dialogResult[0];
