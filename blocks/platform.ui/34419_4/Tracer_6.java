package org.eclipse.ui.internal.monitoring;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.internal.monitoring.preferences.MonitoringPreferenceListener;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class MonitoringStartup implements IStartup {
	private EventLoopMonitorThread monitoringThread;

	@Override
	public void earlyStartup() {
		if (monitoringThread != null) {
			return;
		}

		IPreferenceStore preferences = MonitoringPlugin.getDefault().getPreferenceStore();
		if (preferences.getBoolean(PreferenceConstants.MONITORING_ENABLED)) {
			monitoringThread = createAndStartMonitorThread();
		}

		preferences.addPropertyChangeListener(new MonitoringPreferenceListener(monitoringThread));
	}

	public static EventLoopMonitorThread createAndStartMonitorThread() {
		EventLoopMonitorThread.Parameters args = loadPreferences();
		EventLoopMonitorThread temporaryThread = null;

		try {
			temporaryThread = new EventLoopMonitorThread(args);
		} catch (IllegalArgumentException e) {
			MonitoringPlugin.logError(Messages.MonitoringStartup_initialization_error, e);
			return null;
		}

		final EventLoopMonitorThread thread = temporaryThread;
		final Display display = MonitoringPlugin.getDefault().getWorkbench().getDisplay();
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				display.disposeExec(new Runnable() {
					@Override
					public void run() {
						thread.shutdown();
					}
				});
				thread.start();
			}
		});

		return thread;
	}

	private static EventLoopMonitorThread.Parameters loadPreferences() {
		IPreferenceStore preferences = MonitoringPlugin.getDefault().getPreferenceStore();
		EventLoopMonitorThread.Parameters args = new EventLoopMonitorThread.Parameters();

		args.longEventWarningThreshold =
				preferences.getInt(PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS);
		args.longEventErrorThreshold =
				preferences.getInt(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS);
		args.deadlockThreshold =
				preferences.getInt(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS);
		args.maxStackSamples = preferences.getInt(PreferenceConstants.MAX_STACK_SAMPLES);
		args.uiThreadFilter = preferences.getString(PreferenceConstants.UI_THREAD_FILTER);
		args.noninterestingThreadFilter =
				preferences.getString(PreferenceConstants.NONINTERESTING_THREAD_FILTER);
		args.logToErrorLog = preferences.getBoolean(PreferenceConstants.LOG_TO_ERROR_LOG);

		return args;
	}
}
