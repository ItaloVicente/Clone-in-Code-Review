package org.eclipse.egit.core.internal.start;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.prefs.BackingStoreException;

@Component
public class PreferencesMigrator {

	@Reference
	void setPreferencesService(
			@SuppressWarnings("unused") IPreferencesService service) {
	}

	@Reference
	void setWorkspace(@SuppressWarnings("unused") IWorkspace workspace) {
	}

	@Activate
	void start() {
		IEclipsePreferences corePrefs = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		boolean changed = false;
		IEclipsePreferences uiPrefs = InstanceScope.INSTANCE
				.getNode("org.eclipse.egit.ui"); //$NON-NLS-1$
		String old_ui_preference_key = "remote_connection_timeout"; //$NON-NLS-1$
		int timeout = corePrefs
				.getInt(GitCorePreferences.core_remoteConnectionTimeout, -1);
		if (timeout < 0) {
			timeout = uiPrefs.getInt(old_ui_preference_key, -1);
			if (timeout > 0) {
				corePrefs.putInt(
						GitCorePreferences.core_remoteConnectionTimeout,
						timeout);
				uiPrefs.remove(old_ui_preference_key);
				changed = true;
			}
		}
		if (changed) {
			try {
				corePrefs.flush();
				uiPrefs.flush();
			} catch (BackingStoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
	}
}
