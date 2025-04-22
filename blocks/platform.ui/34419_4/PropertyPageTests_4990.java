package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceNode;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class PreferencePageTests extends DynamicTestCase {

	public PreferencePageTests(String testName) {
		super(testName);
	}

	public void testPreferences() {
		PreferenceManager preferenceManager = PlatformUI.getWorkbench().getPreferenceManager();
		assertNull(preferenceManager.find("dynamic.parentPage"));
		assertNull(preferenceManager.find("dynamic.parentPage/dynamic.childPage"));
		getBundle();
		WorkbenchPreferenceNode node = (WorkbenchPreferenceNode) preferenceManager.find("dynamic.parentPage");
		assertNotNull(node);
		node.createPage();
		node = (WorkbenchPreferenceNode) preferenceManager.find("dynamic.parentPage/dynamic.childPage");
		assertNotNull(node);
		node.createPage();
		removeBundle();
		assertNull(preferenceManager.find("dynamic.parentPage"));
		assertNull(preferenceManager.find("dynamic.parentPage/dynamic.childPage"));
	}

	@Override
	protected String getExtensionId() {
		return "newPreferencePage1.testDynamicPreferencePageAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_PREFERENCES;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newPreferencePage1";
	}

	@Override
	protected String getMarkerClass() {
		return "org.eclipse.ui.dynamic.DynamicPreferencePage";
	}
}
