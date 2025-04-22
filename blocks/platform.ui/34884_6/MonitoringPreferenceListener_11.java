package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class MonitoringPreferenceInitializer extends AbstractPreferenceInitializer {
	private static final int DEFAULT_FORCE_DEADLOCK_LOG_TIME_MILLIS = 10 * 60 * 1000; // 10 minutes

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = MonitoringPlugin.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.MONITORING_ENABLED, false);
		store.setDefault(PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS, 500); // 0.5 sec
		store.setDefault(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS, 2000); // 2 sec
		store.setDefault(PreferenceConstants.MAX_STACK_SAMPLES, 3);
		store.setDefault(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS,
				DEFAULT_FORCE_DEADLOCK_LOG_TIME_MILLIS);
		store.setDefault(PreferenceConstants.LOG_TO_ERROR_LOG, true);
		store.setDefault(PreferenceConstants.FILTER_TRACES, ""); //$NON-NLS-1$
	}
}
