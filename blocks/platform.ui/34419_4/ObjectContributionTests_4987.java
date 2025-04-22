package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.wizards.IWizardRegistry;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class NewWizardTests extends DynamicTestCase {

	private static final String WIZARD_ID = "org.eclipse.newNewWizard1.newNewWizard1";

	public NewWizardTests(String testName) {
		super(testName);
	}

	public void testNewWizardProperties() {
		IWizardRegistry registry = WorkbenchPlugin.getDefault().getNewWizardRegistry();
		assertNull(registry.findWizard(WIZARD_ID));
		getBundle();
		IWizardDescriptor wizard = registry.findWizard(WIZARD_ID);
		assertNotNull(wizard);
		testNewWizardProperties(wizard);
		removeBundle();
		assertNull(registry.findWizard(WIZARD_ID));
		try {
			testNewWizardProperties(wizard);
			fail();
		}
		catch (RuntimeException e) {
		}
	}

	private void testNewWizardProperties(IWizardDescriptor wizard) {
		assertNotNull(wizard.getId());
		assertNotNull(wizard.getDescription());
		assertNotNull(wizard.getHelpHref());
		assertNotNull(wizard.getDescriptionImage());
		assertNotNull(wizard.getImageDescriptor());
	}

	@Override
	protected String getExtensionId() {
		return "newNewWizard1.testDynamicNewWizardAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_NEW;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newNewWizard1";
	}

	@Override
	protected String getMarkerClass() {
		return "org.eclipse.ui.dynamic.DynamicWizard";
	}
}
