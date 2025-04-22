
package org.eclipse.ui.tests.keys;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PluginVersionIdentifier;
import org.eclipse.core.runtime.Preferences;

public abstract class PreferenceMutator {
    static final void setKeyBinding(String commandId, String keySequenceText)
            throws CoreException, FileNotFoundException, IOException {
        Properties preferences = new Properties();
        String key = "org.eclipse.ui.workbench/org.eclipse.ui.commands"; //$NON-NLS-1$
        String value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<org.eclipse.ui.commands><activeKeyConfiguration/><keyBinding commandId=\"" + commandId + "\" keySequence=\"" + keySequenceText + "\"/></org.eclipse.ui.commands>"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        preferences.put(key, value);
        
        String[] pluginIds = Platform.getExtensionRegistry().getNamespaces();
		for (int i = 0; i < pluginIds.length; i++) {
			preferences.put(pluginIds[i], new PluginVersionIdentifier(
					Platform.getBundle(pluginIds[i]).getHeaders().get(
							org.osgi.framework.Constants.BUNDLE_VERSION)));
		}

        File file = File.createTempFile("preferences", ".txt"); //$NON-NLS-1$//$NON-NLS-2$
        file.deleteOnExit();
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file));
        preferences.store(bos, null);
        bos.close();

        Preferences.importPreferences(new Path(file.getAbsolutePath()));
    }

}
