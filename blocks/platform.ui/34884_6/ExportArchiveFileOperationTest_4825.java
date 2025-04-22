package org.eclipse.ui.tests.datatransfer;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DataTransferTestSuite extends TestSuite {

    public static Test suite() {
        return new DataTransferTestSuite();
    }

    public DataTransferTestSuite() {
        addTest(new TestSuite(ImportOperationTest.class));
        addTest(new TestSuite(ImportArchiveOperationTest.class)); 
        addTest(new TestSuite(ExportFileSystemOperationTest.class));
        addTest(new TestSuite(ExportArchiveFileOperationTest.class));
        addTest(ImportExistingProjectsWizardTest.suite());
        addTest(new TestSuite(ImportExportWizardsCategoryTests.class));
    }
}
