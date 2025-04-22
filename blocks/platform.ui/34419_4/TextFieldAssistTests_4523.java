
package org.eclipse.jface.tests.fieldassist;

import junit.framework.Test;
import junit.framework.TestSuite;

public class FieldAssistTestSuite extends TestSuite {
	public static final Test suite() {
		return new FieldAssistTestSuite();
	}

	public FieldAssistTestSuite() {
		addTest(new TestSuite(ControlDecorationTests.class));
		addTest(new TestSuite(FieldAssistAPITests.class));
	}
}
