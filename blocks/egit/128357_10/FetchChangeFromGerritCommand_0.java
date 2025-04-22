			Wizard wizard = new Wizard() {

				@Override
				public boolean performFinish() {
					return true;
				}
			};
			wizard.addPage(page);
			WizardDialog wizardDialog = new WizardDialog(shell, wizard);
			int result = wizardDialog.open();
			if (result != Window.OK) {
				return null;
			}
			repository = page.getRepository();
