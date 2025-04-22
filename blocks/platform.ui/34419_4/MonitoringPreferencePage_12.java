package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.monitoring.EventLoopMonitorThread;
import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
import org.eclipse.ui.internal.monitoring.MonitoringStartup;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class MonitoringPreferenceListener implements IPropertyChangeListener {
	private EventLoopMonitorThread monitoringThread;
	private boolean monitorThreadRestartInProgress;

	public MonitoringPreferenceListener(EventLoopMonitorThread thread) {
		monitoringThread = thread;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (!property.equals(PreferenceConstants.MONITORING_ENABLED)
				&& !property.equals(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS)
				&& !property.equals(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS)
				&& !property.equals(PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS)
				&& !property.equals(PreferenceConstants.LOG_TO_ERROR_LOG)
				&& !property.equals(PreferenceConstants.MAX_STACK_SAMPLES)
				&& !property.equals(PreferenceConstants.UI_THREAD_FILTER)
				&& !property.equals(PreferenceConstants.NONINTERESTING_THREAD_FILTER)) {
			return;
		}

		synchronized (this) {
			if (monitorThreadRestartInProgress) {
				return;
			}

			monitorThreadRestartInProgress = true;

			final Display display = MonitoringPlugin.getDefault().getWorkbench().getDisplay();
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					refreshMonitoringThread();
				}
			});
		}
	}

	private synchronized void refreshMonitoringThread() {
		if (monitoringThread != null) {
			monitoringThread.shutdown();
			monitoringThread = null;
		}
		monitorThreadRestartInProgress = false;

		MonitoringPlugin plugin = MonitoringPlugin.getDefault();
		IPreferenceStore preferences = plugin.getPreferenceStore();
		if (preferences.getBoolean(PreferenceConstants.MONITORING_ENABLED)) {
			EventLoopMonitorThread thread = MonitoringStartup.createAndStartMonitorThread();
			if (thread == null) {
				MessageDialog.openError(
						plugin.getWorkbench().getActiveWorkbenchWindow().getShell(),
						Messages.MonitoringPreferenceListener_preference_error_header,
						Messages.MonitoringPreferenceListener_preference_error);
				return;
			}

			monitoringThread = thread;
		}
	}
}
