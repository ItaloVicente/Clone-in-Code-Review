package org.eclipse.ui.internal.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.osgi.service.prefs.BackingStoreException;


public class PrefUtil {

    private PrefUtil() {
    }

    public static interface ICallback {
        IPreferenceStore getPreferenceStore();

        void savePreferences();
    }

    private static ICallback uiCallback;

    private static IPreferenceStore uiPreferenceStore;

    public static final void setUICallback(ICallback callback) {
        Assert.isTrue(uiCallback == null);
        uiCallback = callback;
    }

    public static IPreferenceStore getAPIPreferenceStore() {
        if (uiPreferenceStore == null) {
            Assert.isNotNull(uiCallback);
            uiPreferenceStore = uiCallback.getPreferenceStore();
        }
        return uiPreferenceStore;
    }

    public static IPreferenceStore getInternalPreferenceStore() {
        return WorkbenchPlugin.getDefault().getPreferenceStore();
    }

    public static void savePrefs() {
        saveAPIPrefs();
        saveInternalPrefs();
    }

    public static void saveAPIPrefs() {
        Assert.isNotNull(uiCallback);
        uiCallback.savePreferences();
    }

    public static void saveInternalPrefs() {
		try {
			InstanceScope.INSTANCE.getNode(WorkbenchPlugin.PI_WORKBENCH).flush();
		} catch (BackingStoreException e) {
			WorkbenchPlugin.log(e);
		}
    }
}
