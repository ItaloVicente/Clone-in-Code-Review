		WizardDialog dialog = new WizardDialog(getShell(event), wizard) {
			@Override
			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				if (id == IDialogConstants.FINISH_ID) {
					return super.createButton(parent, id,
							UIText.AddCommand_AddButtonLabel, defaultButton);
				}
				return super.createButton(parent, id, label, defaultButton);
			}
		};
