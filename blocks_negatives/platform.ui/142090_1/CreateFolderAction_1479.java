        BasicNewFolderResourceWizard wizard = new BasicNewFolderResourceWizard();
        wizard.init(PlatformUI.getWorkbench(), getStructuredSelection());
        wizard.setNeedsProgressMonitor(true);
        WizardDialog dialog = new WizardDialog(shellProvider.getShell(), wizard);
        dialog.create();
        dialog.getShell().setText(
                IDEWorkbenchMessages.CreateFolderAction_title);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_FOLDER_WIZARD);
        dialog.open();
