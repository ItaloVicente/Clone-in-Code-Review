        super.tearDown();
        try {
            if (project != null) {
                project.delete(true, true, null);
                project = null;
            }
        } catch (CoreException e) {
            fail(e.toString());
        }
    }

        Dialog dialog = exportWizard(null);
        DialogCheck.assertDialogTexts(dialog);
    }

    /**
     * 1GJWD2E: ITPUI:ALL - Test classes should not be released in public packages.
     *
     public void testFileSystemExport() {
     Dialog dialog = exportWizard( DataTransferTestStub.newFileSystemResourceExportPage1(null) );
     DialogCheck.assertDialogTexts(dialog);
     }
     public void testZipFileExport() {
     Dialog dialog = exportWizard( DataTransferTestStub.newZipFileResourceExportPage1(null) );
     DialogCheck.assertDialogTexts(dialog);
     }
     */
        Dialog dialog = importWizard(null);
        DialogCheck.assertDialogTexts(dialog);
    }

    /**
     * 1GJWD2E: ITPUI:ALL - Test classes should not be released in public packages.
     *
     public void testFileSystemImport() {
     Dialog dialog = importWizard( DataTransferTestStub.newFileSystemResourceImportPage1(WorkbenchPlugin.getDefault().getWorkbench(), StructuredSelection.EMPTY) );
     DialogCheck.assertDialogTexts(dialog);
     }
     public void testZipFileImport() {
     Dialog dialog = importWizard( DataTransferTestStub.newZipFileResourceImportPage1(null) );
     DialogCheck.assertDialogTexts(dialog);
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
        DialogCheck.assertDialogTexts(dialog);
    }

    /**
     * Test for bug 30719 [Linked Resources] NullPointerException when setting filename for WizardNewFileCreationPage
     */
    public void testNewFile2() {
        BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard() {
            @Override
