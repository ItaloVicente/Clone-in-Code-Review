    /**
     * 1GJWD2E: ITPUI:ALL - Test classes should not be released in public packages.
     *
     public void testFileSystemImport() {
     Dialog dialog = importWizard( DataTransferTestStub.newFileSystemResourceImportPage1(WorkbenchPlugin.getDefault().getWorkbench(), StructuredSelection.EMPTY) );
     DialogCheck.assertDialog(dialog);
     }
     public void testZipFileImport() {
     Dialog dialog = importWizard( DataTransferTestStub.newZipFileResourceImportPage1(null) );
     DialogCheck.assertDialog(dialog);
     }
     */
    public void testNewFile() {
        BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard();
        wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
        wizard.setNeedsProgressMonitor(true);
        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setText("CreateFileAction_title");
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_FILE_WIZARD);
        DialogCheck.assertDialog(dialog);
    }
