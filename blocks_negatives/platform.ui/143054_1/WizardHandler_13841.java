	        wizard.init(activeWorkbenchWindow.getWorkbench(), selectionToPass);
	        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
	                .getDialogSettings();
	        IDialogSettings wizardSettings = workbenchSettings
	        if (wizardSettings == null) {
				wizardSettings = workbenchSettings
