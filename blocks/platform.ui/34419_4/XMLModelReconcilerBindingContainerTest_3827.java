
package org.eclipse.e4.ui.tests.reconciler.xml;

import org.eclipse.e4.ui.internal.workbench.ModelReconcilingService;
import org.eclipse.e4.ui.tests.reconciler.ModelReconcilerApplicationTest;
import org.eclipse.e4.ui.workbench.modeling.IModelReconcilingService;

public class XMLModelReconcilerApplicationTest extends
		ModelReconcilerApplicationTest {

	@Override
	protected IModelReconcilingService getModelReconcilingService() {
		return new ModelReconcilingService();
	}

}
