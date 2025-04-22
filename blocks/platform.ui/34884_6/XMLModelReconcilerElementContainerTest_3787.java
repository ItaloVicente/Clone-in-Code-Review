
package org.eclipse.e4.ui.tests.reconciler.xml;

import org.eclipse.e4.ui.internal.workbench.ModelReconcilingService;
import org.eclipse.e4.ui.tests.reconciler.ModelReconcilerContributionTest;
import org.eclipse.e4.ui.workbench.modeling.IModelReconcilingService;

public class XMLModelReconcilerContributionTest extends
		ModelReconcilerContributionTest {

	@Override
	protected IModelReconcilingService getModelReconcilingService() {
		return new ModelReconcilingService();
	}

}
