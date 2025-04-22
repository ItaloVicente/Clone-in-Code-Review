@RunWith(org.junit.runners.AllTests.class)
public class ModelReconcilerTestSuite extends TestSuite {

	public static Test suite() {
		return new ModelReconcilerTestSuite();
	}

	public ModelReconcilerTestSuite() {
		addTestSuite(E4XMIResourceFactoryTest.class);
		addTest(XMLModelReconcilerTestSuite.suite());
	}

