					WizardDialog dlg = new WizardDialog(getShell(), wizard) {

						@Override
						protected Button createButton(Composite container,
								int id, String label, boolean defaultButton) {
							if (id == IDialogConstants.FINISH_ID) {
								return super.createButton(container, id,
										UIText.AddCommand_AddButtonLabel,
										defaultButton);
							}
							return super.createButton(container, id, label,
									defaultButton);
						}
					};
