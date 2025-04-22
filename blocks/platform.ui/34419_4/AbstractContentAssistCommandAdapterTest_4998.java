package org.eclipse.ui.tests.encoding;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EncodingTestSuite extends TestSuite {

	public static Test suite() {
		return new EncodingTestSuite();
	}

	public EncodingTestSuite() {
		super();
		addTest(new TestSuite(EncodingTestCase.class));
	}



}
