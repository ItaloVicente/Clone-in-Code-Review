				String[] buttonLabels = { UIText.StashDropCommand_buttonDelete,
						IDialogConstants.CANCEL_LABEL };

				MessageDialog confirmDialog = new MessageDialog(shell,
						UIText.StashDropCommand_confirmTitle, null, message,
						MessageDialog.CONFIRM, buttonLabels, 0);

				confirmed.set(confirmDialog.open() == Window.OK);
