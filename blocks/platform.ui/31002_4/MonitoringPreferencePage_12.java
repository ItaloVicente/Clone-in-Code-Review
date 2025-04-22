package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.monitoring.EventLoopMonitorThread;
import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
import org.eclipse.ui.internal.monitoring.MonitoringStartup;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class MonitoringPreferenceListener implements IPropertyChangeListener {
	private EventLoopMonitorThread monitoringThread;
	private volatile boolean monitorThreadRestartInProgress;

	public MonitoringPreferenceListener(EventLoopMonitorThread thread) {
		monitoringThread = thread;
		monitorThreadRestartInProgress = false;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (property.equals(PreferenceConstants.MONITORING_ENABLED)) {
			if (monitoringThread != null && event.getNewValue().equals(false)) {
				monitoringThread.shutdown();
				monitoringThread = null;
			} else if (monitoringThread == null && event.getNewValue().equals(true)) {
				monitoringThread = MonitoringStartup.createAndStartMonitorThread();
			}
		} else if (property.equals(PreferenceConstants.FORCE_DEADLOCK_LOG_TIME_MILLIS)
				|| property.equals(PreferenceConstants.MAX_LOG_TRACE_COUNT)
				|| property.equals(PreferenceConstants.MAX_EVENT_LOG_TIME_MILLIS)
				|| property.equals(PreferenceConstants.MAX_EVENT_SAMPLE_TIME_MILLIS)
				|| property.equals(PreferenceConstants.SAMPLE_INTERVAL_TIME_MILLIS)
				|| property.equals(PreferenceConstants.DUMP_ALL_THREADS)
				|| property.equals(PreferenceConstants.LOG_TO_ERROR_LOG)
				|| property.equals(PreferenceConstants.FILTER_TRACES)) {
			restartMonitoringThread();
		}
	}

	private void restartMonitoringThread() {
		if (monitoringThread == null || monitorThreadRestartInProgress) {
			return;
		}

		monitorThreadRestartInProgress = true;

		final Display display = MonitoringPlugin.getDefault().getWorkbench().getDisplay();
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				monitorThreadRestartInProgress = false;
				EventLoopMonitorThread thread = MonitoringStartup.createAndStartMonitorThread();
				if (thread == null) {
					MessageDialog.openInformation(
							MonitoringPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(),
							Messages.MonitoringPreferenceListener_preference_error_header,
							Messages.MonitoringPreferenceListener_preference_error);
					return;
				}

				monitoringThread.shutdown();
				monitoringThread = thread;
			}
		});
	}
}
