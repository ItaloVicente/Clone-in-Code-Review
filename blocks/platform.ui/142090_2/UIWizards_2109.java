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
