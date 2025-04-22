import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(org.junit.runners.AllTests.class)
public class DecoratorsTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new DecoratorsTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public DecoratorsTestSuite() {
		addTest(new TestSuite(ExceptionDecoratorTestCase.class));
		addTest(new TestSuite(DecoratorTestCase.class));
		addTest(new TestSuite(LightweightDecoratorTestCase.class));
		addTest(new TestSuite(BadIndexDecoratorTestCase.class));
		addTest(DecoratorAdaptableTests.suite());
		addTest(new TestSuite(DecoratorCacheTest.class));
	}

