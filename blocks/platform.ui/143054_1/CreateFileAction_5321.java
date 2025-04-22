		BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), getStructuredSelection());
		wizard.setNeedsProgressMonitor(true);
		WizardDialog dialog = new WizardDialog(shellProvider.getShell(), wizard);
		dialog.create();
		dialog.getShell().setText(
				IDEWorkbenchMessages.CreateFileAction_title);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
				IIDEHelpContextIds.NEW_FILE_WIZARD);
		dialog.open();
	}
