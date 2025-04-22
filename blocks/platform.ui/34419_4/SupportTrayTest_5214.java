
package org.eclipse.ui.tests.statushandlers;

import junit.framework.Test;
import junit.framework.TestSuite;

public class StatusHandlingTestSuite extends TestSuite {

	public StatusHandlingTestSuite() {
		addTest(new TestSuite(WizardsStatusHandlingTestCase.class));
		addTest(new TestSuite(StatusDialogManagerTest.class));
		addTest(new TestSuite(LabelProviderWrapperTest.class));
		addTest(new TestSuite(SupportTrayTest.class));
		addTest(new TestSuite(WorkbenchStatusDialogManagerImplTest.class));
	}

	public static Test suite() {
		return new StatusHandlingTestSuite();
	}
}
