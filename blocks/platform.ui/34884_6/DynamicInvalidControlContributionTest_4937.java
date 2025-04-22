
package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class DynamicInvalidContributionTest extends DynamicTestCase {

	public DynamicInvalidContributionTest(String testName) {
		super(testName);
	}

	public void testInvalidMenuContribution() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		getBundle();
		fWorkbench.openWorkbenchWindow(window.getActivePage().getPerspective().getId(), null);
	}

	@Override
	protected String getExtensionId() {
		return "menu.invalid.menu.contribution";
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
