package org.eclipse.ui.internal.monitoring;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.internal.monitoring.EventLoopMonitorThread.InitializationException;
import org.eclipse.ui.internal.monitoring.preferences.MonitoringPreferenceListener;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class MonitoringStartup implements IStartup {
	private EventLoopMonitorThread monitoringThread;

	@Override
	public void earlyStartup() {
		setupPlugin();
	}

	private void setupPlugin() {
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
		} catch (InitializationException e) {
			MonitoringPlugin.logError(Messages.MonitoringStartup_initialization_error, e);
			return null;
		}

		final EventLoopMonitorThread thread = temporaryThread;
		final Display display = MonitoringPlugin.getDefault().getWorkbench().getDisplay();
		display.syncExec(new Runnable() {
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

		args.loggingThreshold = preferences.getInt(PreferenceConstants.MAX_EVENT_LOG_TIME_MILLIS);
		args.samplingThreshold = preferences.getInt(PreferenceConstants.MAX_EVENT_SAMPLE_TIME_MILLIS);
		args.dumpAllThreads = preferences.getBoolean(PreferenceConstants.DUMP_ALL_THREADS);
		args.minimumPollingDelay = preferences.getInt(PreferenceConstants.SAMPLE_INTERVAL_TIME_MILLIS);
		args.loggedTraceCount = preferences.getInt(PreferenceConstants.MAX_LOG_TRACE_COUNT);
		args.deadlockDelta = preferences.getInt(PreferenceConstants.FORCE_DEADLOCK_LOG_TIME_MILLIS);
		args.logLocally = preferences.getBoolean(PreferenceConstants.LOG_TO_ERROR_LOG);
		args.filterTraces = preferences.getString(PreferenceConstants.FILTER_TRACES);

		return args;
	}
}
