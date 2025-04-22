
package org.eclipse.e4.ui.tests.reconciler.xml;

import org.eclipse.e4.ui.internal.workbench.ModelReconcilingService;
import org.eclipse.e4.ui.tests.reconciler.IModelReconcilingServiceTest;
import org.eclipse.e4.ui.workbench.modeling.IModelReconcilingService;

public class ModelReconcilingServiceTest extends IModelReconcilingServiceTest {

	@Override
	protected IModelReconcilingService getModelReconcilingService() {
		return new ModelReconcilingService();
	}

}
