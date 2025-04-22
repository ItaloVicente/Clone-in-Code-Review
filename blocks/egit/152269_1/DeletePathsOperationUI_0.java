		String[] buttonLabels = { UIText.DeletePathsOperationUI_ButtonOK,
				IDialogConstants.CANCEL_LABEL };
		MessageDialog dialog = new MessageDialog(shellProvider.getShell(),
				UIText.DeleteResourcesOperationUI_confirmActionTitle, null,
				UIText.DeleteResourcesOperationUI_confirmActionMessage,
				MessageDialog.CONFIRM, buttonLabels, 0);
		if (dialog.open() != Window.OK) {
