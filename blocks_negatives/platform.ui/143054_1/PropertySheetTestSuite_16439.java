@RunWith(org.junit.runners.AllTests.class)
public class PropertySheetTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new PropertySheetTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public PropertySheetTestSuite() {
        addTest(new TestSuite(PropertyShowInContextTest.class));
        addTest(new TestSuite(MultiInstancePropertySheetTest.class));
        addTest(new TestSuite(ShowInPropertySheetTest.class));
        addTest(new TestSuite(NewPropertySheetHandlerTest.class));
        addTest(new TestSuite(PropertySheetAuto.class));
        addTest(new TestSuite(ComboBoxPropertyDescriptorTest.class));
        addTest(new TestSuite(DirtyStatePropertySheetTest.class));
    }
