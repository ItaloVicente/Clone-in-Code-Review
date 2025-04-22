package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class CancelabilityMonitorPreferenceInitializer extends AbstractPreferenceInitializer {
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = MonitoringPlugin.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.TASK_MONITORING_ENABLED, false);
		store.setDefault(PreferenceConstants.TASK_MONITORING_WARNING_THRESHOLD_MILLIS, 1000); // 2 sec
		store.setDefault(PreferenceConstants.TASK_MONITORING_ERROR_THRESHOLD_MILLIS, 3000); // 3 sec
		store.setDefault(PreferenceConstants.TASK_MONITORING_MAX_STACK_SAMPLES, 3);
		store.setDefault(PreferenceConstants.TASK_MONITORING_LOG_NON_CANCELLABLE_USER_JOB, true);
	}
}
