package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.internal.help.WorkbenchHelpSystem;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class HelpSupportTests extends DynamicTestCase {

	public HelpSupportTests(String testName) {
		super(testName);
	}

	public void testHelpSupport() {
		WorkbenchHelpSystem help = WorkbenchHelpSystem.getInstance();
		help.setDesiredHelpSystemId(getExtensionId());
		assertFalse(help.hasHelpUI());
		
		getBundle();
		help.dispose();
		assertTrue(help.hasHelpUI());
		
		removeBundle();
		help.dispose();		
		assertFalse(help.hasHelpUI());
		
		help.setDesiredHelpSystemId(null);		
	}
	
	@Override
	protected String getExtensionId() {
		return "newHelpSupport1.testDynamicHelpSupportAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_HELPSUPPORT;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newHelpSupport1";
	}
	
	@Override
	protected String getMarkerClass() {
		return "org.eclipse.ui.dynamic.DynamicHelpSupport";
	}

}
