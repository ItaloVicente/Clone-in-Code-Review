				@Override
				public boolean performFinish() {
					FetchChangeFromGerritCommand.this.repository = page
							.getRepository();
					return true;
				}
			};
			wizard.addPage(page);
			wizard.setWindowTitle(UIText.GerritSelectRepositoryPage_PageTitle);
			WizardDialog wizardDialog = new WizardDialog(shell, wizard) {
				@Override
				protected Button createButton(Composite parent, int id,
						String label, boolean defaultButton) {
					String text = label;
					if (id == IDialogConstants.FINISH_ID) {
						text = UIText.GerritSelectRepositoryPage_FinishButtonLabel;
					}
					return super.createButton(parent, id, text, defaultButton);
				}
			};
			wizardDialog.setHelpAvailable(false);
			int result = wizardDialog.open();
			if (result != Window.OK) {
				return null;
			}
