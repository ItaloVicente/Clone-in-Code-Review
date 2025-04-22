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

	public void testExportResources() {//reference: ExportResourcesAction
		Dialog dialog = exportWizard(null);
		DialogCheck.assertDialogTexts(dialog);
	}

	 public void testFileSystemExport() {
	 Dialog dialog = exportWizard( DataTransferTestStub.newFileSystemResourceExportPage1(null) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
	 public void testZipFileExport() {
	 Dialog dialog = exportWizard( DataTransferTestStub.newZipFileResourceExportPage1(null) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
	public void testImportResources() {//reference: ImportResourcesAction
		Dialog dialog = importWizard(null);
		DialogCheck.assertDialogTexts(dialog);
	}

	 public void testFileSystemImport() {
	 Dialog dialog = importWizard( DataTransferTestStub.newFileSystemResourceImportPage1(WorkbenchPlugin.getDefault().getWorkbench(), StructuredSelection.EMPTY) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
	 public void testZipFileImport() {
	 Dialog dialog = importWizard( DataTransferTestStub.newZipFileResourceImportPage1(null) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
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

	public void testNewFile2() {
		BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard() {
			@Override
