package org.eclipse.ui.tests.dynamicplugins;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.tests.TestPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class DynamicUtils {

    public static final Bundle installPlugin(String pluginName)
            throws IOException, BundleException {
        TestPlugin plugin = TestPlugin.getDefault();
        if (plugin == null)
            throw new IllegalStateException(
                    "TestPlugin default reference is null");
        String pluginLocation = null;
        URL dataURL = Platform.resolve(plugin.getBundle().getEntry(pluginName));
        pluginLocation = "reference:" + dataURL.toExternalForm();
        return TestInstallUtil.installBundle(pluginLocation);
    }

    public static void uninstallPlugin(Bundle bundle) throws BundleException {
        TestInstallUtil.uninstallBundle(bundle);
    }

}
