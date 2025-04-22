		wizard.init(workbench, selectionToPass);
		IDialogSettings workbenchSettings = IDEWorkbenchPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings
				.getSection("NewWizardAction"); //$NON-NLS-1$
		if (wizardSettings == null) {
