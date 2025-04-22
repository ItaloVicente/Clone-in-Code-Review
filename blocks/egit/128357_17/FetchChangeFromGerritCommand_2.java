				@Override
				public boolean performFinish() {
					FetchChangeFromGerritCommand.this.repository = page
							.getRepository();
					return true;
				}
			};
			wizard.addPage(page);
			wizard.setWindowTitle(UIText.GerritSelectRepositoryPage_PageTitle);
			WizardDialog wizardDialog = new WizardDialog(shell, wizard);
			wizardDialog.setHelpAvailable(false);
			int result = wizardDialog.open();
			if (result != Window.OK) {
				return null;
			}
