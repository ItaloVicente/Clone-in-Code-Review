	 *
	 public void testFileSystemExport() {
	 Dialog dialog = exportWizard( DataTransferTestStub.newFileSystemResourceExportPage1(null) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
	 public void testZipFileExport() {
	 Dialog dialog = exportWizard( DataTransferTestStub.newZipFileResourceExportPage1(null) );
	 DialogCheck.assertDialogTexts(dialog);
	 }
