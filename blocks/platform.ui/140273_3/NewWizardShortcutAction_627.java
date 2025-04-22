		Shell parent = window.getShell();
		WizardDialog dialog = new WizardDialog(parent, wizard);
		dialog.create();
		Point defaultSize = dialog.getShell().getSize();
		dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, defaultSize.x),
				Math.max(SIZING_WIZARD_HEIGHT, defaultSize.y));
		window.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IWorkbenchHelpContextIds.NEW_WIZARD_SHORTCUT);

		if (wizardElement.canFinishEarly() && !wizardElement.hasPages()) {
