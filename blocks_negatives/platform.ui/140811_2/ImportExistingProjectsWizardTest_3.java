	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new ImportExistingProjectsWizardTest("testFindSingleZip"));
		ts.addTest(new ImportExistingProjectsWizardTest("testFindSingleTar"));
		ts.addTest(new ImportExistingProjectsWizardTest("testFindSingleDirectory"));
		ts.addTest(new ImportExistingProjectsWizardTest("testDoNotShowProjectWithSameName"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportSingleZip"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportZipWithEmptyFolder"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportSingleTar"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportTarWithEmptyFolder"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportSingleDirectory"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportSingleDirectoryWithCopy"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportSingleDirectoryWithCopyDeleteProjectKeepContents"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportZipDeleteContentsImportAgain"));
		ts.addTest(new ImportExistingProjectsWizardTest("testInitialValue"));
		ts.addTest(new ImportExistingProjectsWizardTest("testImportArchiveMultiProject"));
		ts.addTest(new ImportExistingProjectsWizardTest("testGetProjectRecords"));
		ts.addTest(new ImportExistingProjectsWizardTest("testGetProjectRecordsShouldHandleCorruptProjects"));
		ts.addTest(new ImportExistingProjectsWizardTest(
				"testGetProjectRecordsShouldHandleCorruptAndConflictingProjects"));
		return ts;
	}

	public ImportExistingProjectsWizardTest(String testName) {
		super(testName);
