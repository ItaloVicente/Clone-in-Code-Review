		if (page != null) {
			page.setWizard(wizard);
			dialog.showPage(page);
		}
		return dialog;
	}

	public void testExportResources() {//reference: ExportResourcesAction
		Dialog dialog = exportWizard(null);
		DialogCheck.assertDialog(dialog);
	}

	 public void testFileSystemExport() {
	 Dialog dialog = exportWizard( DataTransferTestStub.newFileSystemResourceExportPage1(null) );
	 DialogCheck.assertDialog(dialog);
	 }
	 public void testZipFileExport() {
	 Dialog dialog = exportWizard( DataTransferTestStub.newZipFileResourceExportPage1(null) );
	 DialogCheck.assertDialog(dialog);
	 }
	public void testImportResources() {//reference: ImportResourcesAction
		Dialog dialog = importWizard(null);
		DialogCheck.assertDialog(dialog);
	}

	 public void testFileSystemImport() {
	 Dialog dialog = importWizard( DataTransferTestStub.newFileSystemResourceImportPage1(WorkbenchPlugin.getDefault().getWorkbench(), StructuredSelection.EMPTY) );
	 DialogCheck.assertDialog(dialog);
	 }
	 public void testZipFileImport() {
	 Dialog dialog = importWizard( DataTransferTestStub.newZipFileResourceImportPage1(null) );
	 DialogCheck.assertDialog(dialog);
	 }
	public void testNewFile() {
		BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
		wizard.setNeedsProgressMonitor(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setText("CreateFileAction_title");
