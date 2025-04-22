	 *
	 public void testFileSystemImport() {
	 Dialog dialog = importWizard( DataTransferTestStub.newFileSystemResourceImportPage1(WorkbenchPlugin.getDefault().getWorkbench(), StructuredSelection.EMPTY) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
	 public void testZipFileImport() {
	 Dialog dialog = importWizard( DataTransferTestStub.newZipFileResourceImportPage1(null) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
