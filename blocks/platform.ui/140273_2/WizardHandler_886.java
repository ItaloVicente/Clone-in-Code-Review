			wizard.setDialogSettings(wizardSettings);
			wizard.setForcePreviousAndNextButtons(true);

			Shell parent = activeWorkbenchWindow.getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
					SIZING_WIZARD_HEIGHT);
			activeWorkbenchWindow.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
					IWorkbenchHelpContextIds.NEW_WIZARD);
			dialog.open();
