		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);

		Shell parent = workbenchWindow.getShell();
		WizardDialog dialog = new WizardDialog(parent, wizard);
		dialog.create();
		dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IWorkbenchHelpContextIds.NEW_WIZARD);
		dialog.open();
	}

	@Override
