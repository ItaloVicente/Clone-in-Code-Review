		wizard.init(getWorkbench(), selectionToPass);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings
				.getSection("NewWizardAction");//$NON-NLS-1$
		if (wizardSettings == null)
