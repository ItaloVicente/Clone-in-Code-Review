package org.eclipse.ui.tests.decorators;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DecoratorsTestSuite extends TestSuite {

	public static Test suite() {
		return new DecoratorsTestSuite();
	}

	public DecoratorsTestSuite() {
		addTest(new TestSuite(ExceptionDecoratorTestCase.class));
		addTest(new TestSuite(DecoratorTestCase.class));
		addTest(new TestSuite(LightweightDecoratorTestCase.class));
		addTest(new TestSuite(BadIndexDecoratorTestCase.class));
		addTest(DecoratorAdaptableTests.suite());
		addTest(new TestSuite(DecoratorCacheTest.class));
	}

}
