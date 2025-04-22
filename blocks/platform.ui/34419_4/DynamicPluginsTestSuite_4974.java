
package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class DynamicInvalidControlContributionTest extends DynamicTestCase {

	public DynamicInvalidControlContributionTest(String testName) {
		super(testName);
	}

	public void testInvalidControlContribution() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		getBundle();
		fWorkbench.openWorkbenchWindow(window.getActivePage().getPerspective()
				.getId(), null);
	}

	@Override
	protected String getExtensionId() {
		return "menu.invalid.toolbar.contribution.bug371611";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_MENUS;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newInvalidMenuContribution1";
	}

}
