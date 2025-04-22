
	public static class ConfirmDeleteStashDialog extends MessageDialog {

		public ConfirmDeleteStashDialog(Shell parentShell, String message) {
			super(parentShell, UIText.StashDropCommand_confirmTitle, null,
					message,
					MessageDialog.CONFIRM, 0);
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			createButton(parent, IDialogConstants.OK_ID,
					UIText.StashDropCommand_buttonDelete, true);
			createButton(parent, IDialogConstants.CANCEL_ID,
					IDialogConstants.CANCEL_LABEL, false);
		}

	}
