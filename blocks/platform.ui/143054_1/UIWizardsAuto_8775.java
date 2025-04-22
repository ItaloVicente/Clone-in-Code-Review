					.addNewSection("ImportResourcesAction");
		}
		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);

		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(
				Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
				SIZING_WIZARD_HEIGHT);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IWorkbenchHelpContextIds.IMPORT_WIZARD);

		if (page != null) {
			page.setWizard(wizard);
			dialog.showPage(page);
