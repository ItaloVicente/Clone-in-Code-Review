package org.eclipse.egit.core.internal.start;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.prefs.BackingStoreException;

@Component
public class PreferencesMigrator extends Job {

	public PreferencesMigrator() {
		super("Git preferences migration"); //$NON-NLS-1$
		setSystem(true);
		setUser(false);
	}

	@Reference
	void setPreferencesService(
			@SuppressWarnings("unused") IPreferencesService service) {
	}

	@Reference
	void setWorkspace(@SuppressWarnings("unused") IWorkspace workspace) {
	}

	@Activate
	void start() {
		schedule();
	}

	@Deactivate
	void shutDown() {
		cancel();
		try {
			join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			migrate();
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

	private void migrate() {
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
