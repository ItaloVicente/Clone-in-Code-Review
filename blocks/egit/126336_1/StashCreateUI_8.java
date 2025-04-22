		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			createButton(parent, IDialogConstants.OK_ID,
					UIText.StashCreateCommand_ButtonOK, true);
			createButton(parent, IDialogConstants.CANCEL_ID,
					IDialogConstants.CANCEL_LABEL, false);
		}
