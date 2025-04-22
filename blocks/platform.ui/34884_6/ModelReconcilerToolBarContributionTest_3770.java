
package org.eclipse.e4.ui.tests.reconciler;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.e4.ui.tests.reconciler.xml.XMLModelReconcilerTestSuite;

public class ModelReconcilerTestSuite extends TestSuite {

	public static Test suite() {
		return new ModelReconcilerTestSuite();
	}

	public ModelReconcilerTestSuite() {
		addTestSuite(E4XMIResourceFactoryTest.class);
		addTest(XMLModelReconcilerTestSuite.suite());
	}

}
