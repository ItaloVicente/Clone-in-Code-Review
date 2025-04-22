package org.eclipse.ui.examples.rcp.browser;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

public class BrowserPreferenceInitializer extends AbstractPreferenceInitializer {

    public BrowserPreferenceInitializer() {
    }

    public void initializeDefaultPreferences() {
        Preferences prefs = BrowserPlugin.getDefault().getPluginPreferences();
        prefs.setDefault(IBrowserConstants.PREF_HOME_PAGE, "http://eclipse.org");  //$NON-NLS-1$
    }

}
