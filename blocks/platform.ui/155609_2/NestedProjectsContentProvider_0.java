package org.eclipse.ui.internal.navigator.resources;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;

public class NavigatorResourcesPreferenceTester extends PropertyTester {

	private static final String NAVIGATOR_RESOURCES_PREFERENCES = "navigatorResourcePreference"; //$NON-NLS-1$

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!NAVIGATOR_RESOURCES_PREFERENCES.equals(property) || args.length != 1) {
			return false;
		}
		String preferenceName = (String) args[0];
		IPreferenceStore preferenceStore = WorkbenchNavigatorPlugin.getDefault().getPreferenceStore();
		if (expectedValue == null) {
			return preferenceStore.getBoolean(preferenceName);
		}
		return expectedValue.toString().equals(preferenceStore.getString(preferenceName));
	}

}
