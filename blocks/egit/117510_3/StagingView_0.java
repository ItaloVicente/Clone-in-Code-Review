
			MessageDialog dlg = new MessageDialog(form.getShell(),
					UIText.DiscardChangesAction_confirmActionTitle, null,
					question, MessageDialog.CONFIRM,
					new String[] {
							UIText.DiscardChangesAction_discardChangesButtonText,
							IDialogConstants.CANCEL_LABEL },
					0);
			if (dlg.open() != Window.OK) {
