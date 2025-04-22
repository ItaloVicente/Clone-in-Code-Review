import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(org.junit.runners.AllTests.class)
public class DataTransferTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new DataTransferTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public DataTransferTestSuite() {
        addTest(new TestSuite(ImportOperationTest.class));
        addTest(new TestSuite(ImportArchiveOperationTest.class));
        addTest(new TestSuite(ExportFileSystemOperationTest.class));
        addTest(new TestSuite(ExportArchiveFileOperationTest.class));
        addTest(ImportExistingProjectsWizardTest.suite());
        addTest(new TestSuite(ImportExportWizardsCategoryTests.class));
		addTest(new TestSuite(SmartImportTests.class));
		addTest(new TestSuite(ZipSlipTests.class));
    }
