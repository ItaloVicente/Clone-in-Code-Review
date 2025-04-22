
package org.eclipse.e4.ui.tests.reconciler.xml;

import org.eclipse.e4.ui.internal.workbench.ModelReconcilingService;
import org.eclipse.e4.ui.tests.reconciler.ModelReconcilerParameterTest;
import org.eclipse.e4.ui.workbench.modeling.IModelReconcilingService;

public class XMLModelReconcilerParameterTest extends
		ModelReconcilerParameterTest {

	@Override
	protected IModelReconcilingService getModelReconcilingService() {
		return new ModelReconcilingService();
	}

}
