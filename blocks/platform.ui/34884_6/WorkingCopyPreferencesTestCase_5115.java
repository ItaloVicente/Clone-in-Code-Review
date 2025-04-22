
package org.eclipse.ui.tests.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.ui.tests.TestPlugin;

public class TestPreferenceInitializer extends AbstractPreferenceInitializer {

	public static String TEST_LISTENER_KEY = "TEST_LISTENER";

	public static String TEST_SET_VALUE = "TEST_SET_VALUE";

	public static String TEST_DEFAULT_VALUE = "TEST_DEFAULT_VALUE";

	@Override
	public void initializeDefaultPreferences() {

		IScopeContext context = new DefaultScope();
		IEclipsePreferences node = context.getNode(TestPlugin.getDefault()
				.getBundle().getSymbolicName());
		node.put(TEST_LISTENER_KEY, TEST_DEFAULT_VALUE);

	}

}
