	private static class StashCreateDialog extends Dialog {

		private Text text;

		private Button untrackedButton;

		private String value = ""; //$NON-NLS-1$

		private boolean includeUntracked;

		public StashCreateDialog(Shell shell) {
			super(shell);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);

			getShell().setText(
					UIText.StashCreateCommand_titleEnterCommitMessage);

			Label label = new Label(composite, SWT.WRAP);
			label.setText(UIText.StashCreateCommand_messageEnterCommitMessage);
			GridData data = new GridData(GridData.GRAB_HORIZONTAL
					| GridData.GRAB_VERTICAL | GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_CENTER);
			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			label.setLayoutData(data);
			label.setFont(parent.getFont());

			text = new Text(composite, SWT.SINGLE | SWT.BORDER);
			text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
					| GridData.HORIZONTAL_ALIGN_FILL));

			untrackedButton = new Button(composite, SWT.CHECK);
			untrackedButton
					.setText(UIText.StashCreateCommand_includeUntrackedLabel);

			text.setFocus();
			return composite;
		}

		protected void buttonPressed(int buttonId) {
			if (buttonId == IDialogConstants.OK_ID) {
				value = text.getText();
				includeUntracked = untrackedButton.getSelection();
			} else {
				value = null;
				includeUntracked = false;
			}
			super.buttonPressed(buttonId);
		}

		public String getValue() {
			return value;
		}

		public boolean getIncludeUntracked() {
			return includeUntracked;
		}


	}

