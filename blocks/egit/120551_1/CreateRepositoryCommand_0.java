				new NewRepositoryWizard(false)) {
			@Override
			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				if (id == IDialogConstants.FINISH_ID) {
					return super.createButton(parent, id,
							UIText.CreateRepositoryCommand_CreateButtonLabel,
							defaultButton);
				}
				return super.createButton(parent, id, label, defaultButton);
			}
		};
