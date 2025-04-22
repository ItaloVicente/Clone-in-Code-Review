package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class JobCancelabilityMonitorPreferenceInitializer extends AbstractPreferenceInitializer {
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = MonitoringPlugin.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.JOB_MONITORING_ENABLED, false);
		store.setDefault(PreferenceConstants.JOB_MONITORING_WARNING_THRESHOLD_MILLIS, 1000); // 2 sec
		store.setDefault(PreferenceConstants.JOB_MONITORING_ERROR_THRESHOLD_MILLIS, 3000); // 3 sec
		store.setDefault(PreferenceConstants.JOB_MONITORING_MAX_STACK_SAMPLES, 3);
		store.setDefault(PreferenceConstants.JOB_MONITORING_LOG_NON_CANCELLABLE_USER_JOB, true);
	}
}
