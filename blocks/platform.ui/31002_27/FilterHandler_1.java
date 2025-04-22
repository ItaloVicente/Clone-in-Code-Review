package org.eclipse.ui.internal.monitoring;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.monitoring.IUiFreezeEventLogger;
import org.eclipse.ui.monitoring.StackSample;
import org.eclipse.ui.monitoring.UiFreezeEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class EventLoopMonitorThread extends Thread implements Listener {
	private static final String EXTENSION_ID = "org.eclipse.ui.monitoring.logger"; //$NON-NLS-1$
	private static final String NEW_LINE_AND_BULLET = "\n* "; //$NON-NLS-1$

	public static class Parameters {
		public int loggingThreshold;
		public int samplingThreshold;
		public boolean dumpAllThreads;
		public int minimumPollingDelay;
		public int loggedTraceCount;
		public long deadlockDelta;
		public boolean logLocally;
		public String filterTraces;


		public void checkParameters() throws IllegalArgumentException {
			StringBuilder error = new StringBuilder();
			if (!(this.loggingThreshold > 0)) {
				error.append(NEW_LINE_AND_BULLET + NLS.bind(
						Messages.EventLoopMonitorThread_logging_threshold_error_1, this.loggingThreshold));
			}
			if (!(this.minimumPollingDelay > 0)) {
				error.append(NEW_LINE_AND_BULLET + NLS.bind(
						Messages.EventLoopMonitorThread_sample_interval_error_1, this.minimumPollingDelay));
			}
			if (!(this.loggedTraceCount > 0)) {
				error.append(NEW_LINE_AND_BULLET + NLS.bind(
						Messages.EventLoopMonitorThread_max_log_count_error_1, this.loggedTraceCount));
			}
			if (!(this.samplingThreshold > 0)) {
				error.append(NEW_LINE_AND_BULLET + NLS.bind(
						Messages.EventLoopMonitorThread_capture_threshold_error_1, this.samplingThreshold));
			}
			if (this.loggingThreshold < this.samplingThreshold) {
				error.append(NEW_LINE_AND_BULLET + Messages.EventLoopMonitorThread_invalid_threshold_error);
			}
			if (!(this.deadlockDelta > 0)) {
				error.append(NEW_LINE_AND_BULLET
						+ NLS.bind(Messages.EventLoopMonitorThread_deadlock_error_1, this.deadlockDelta));
			}

			String errorString = error.toString();
			if (!errorString.isEmpty()) {
				throw new IllegalArgumentException(
						Messages.EventLoopMonitorThread_invalid_argument_error + errorString);
			}
		}
	}

	private volatile long eventStartOrResumeTime;

	private final int loggingThreshold;
	private final AtomicBoolean cancelled = new AtomicBoolean(false);
	private final AtomicReference<LongEventInfo> publishEvent =
			new AtomicReference<LongEventInfo>(null);

	private ArrayList<IUiFreezeEventLogger> externalLoggers;
	private final DefaultUiFreezeEventLogger defaultLogger;
	private final Tracer localTraceLog;
	private final Display display;
	private final FilterHandler filterHandler;
	private final long samplingThreshold;
	private final long minimumPollingDelay;
	private final int maxTraceCount;
	private final int loggedTraceCount;
	private final long deadlockDelta;
	private final long uiThreadId;
	private final Object sleepMonitor;
	private final boolean dumpAllThreads;
	private final boolean logLocally;

	private class DeadlockTracker {
		private boolean haveAlreadyLoggedPossibleDeadlock;

		private long lastActive;

		public void logPossibleDeadlock(long currTime, StackSample[] stackTraces, int numStacks) {
			long totalDuration = currTime - lastActive;

			if (!haveAlreadyLoggedPossibleDeadlock && lastActive > 0 && totalDuration > deadlockDelta) {
				logEvent(new UiFreezeEvent(lastActive, totalDuration, stackTraces, numStacks, true));
				haveAlreadyLoggedPossibleDeadlock = true;
				Arrays.fill(stackTraces, null);
			}
		}

		public void reset(long lastActive) {
			this.lastActive = lastActive;
			haveAlreadyLoggedPossibleDeadlock = false;
		}
	}

	public EventLoopMonitorThread(Parameters args) throws IllegalArgumentException {
		super("Event Loop Monitor"); //$NON-NLS-1$

		Assert.isNotNull(args);

		args.checkParameters();

		setDaemon(true);
		setPriority(NORM_PRIORITY + 1);
		this.display = getDisplay();
		this.uiThreadId = this.display.getThread().getId();
		this.filterHandler = new FilterHandler(args.filterTraces);
		this.samplingThreshold = args.samplingThreshold;
		this.minimumPollingDelay = args.minimumPollingDelay;
		this.loggedTraceCount = args.loggedTraceCount;
		this.maxTraceCount = 2 * (args.loggedTraceCount - 1);
		this.loggingThreshold = args.loggingThreshold;
		this.dumpAllThreads = args.dumpAllThreads;
		this.localTraceLog = getTracer();
		this.deadlockDelta = args.deadlockDelta;
		this.logLocally = args.logLocally;
		this.sleepMonitor = new Object();
		defaultLogger = new DefaultUiFreezeEventLogger();

		loadLoggerExtensions();

		if (!logLocally && externalLoggers.isEmpty()) {
			MonitoringPlugin.logWarning(Messages.EventLoopMonitorThread_logging_disabled_error);
		}
	}

	public void shutdown() throws SWTException {
		cancelled.set(true);
		if (!display.isDisposed()) {
			display.removeListener(SWT.PreEvent, this);
			display.removeListener(SWT.PostEvent, this);
			display.removeListener(SWT.Sleep, this);
			display.removeListener(SWT.Wakeup, this);
		}
		wakeUp();
	}

	@Override
	public void handleEvent(Event event) {
		switch (event.type) {
		case SWT.PreEvent:
			beginEvent();
			break;
		case SWT.PostEvent:
			endEvent();
			break;
		case SWT.Sleep:
			beginSleep();
			break;
		case SWT.Wakeup:
			endSleep();
			break;
		default:
		}
	}

	public void beginEvent() {
		handleEventTransition(true, false);
	}

	public void endEvent() {
		handleEventTransition(true, false);
	}

	public void beginSleep() {
		handleEventTransition(true, true);
	}

	public void endSleep() {
		handleEventTransition(false, false);
	}

	private void handleEventTransition(boolean attemptToLogLongDelay, boolean isEnteringSleep) {
		long currTime = getTimestamp();
		if (attemptToLogLongDelay) {
			long startTime = eventStartOrResumeTime;
			if (startTime != 0) {
				int duration = (int) (currTime - startTime);
				if (duration >= loggingThreshold) {
					LongEventInfo info = new LongEventInfo(startTime, duration);
					publishEvent.set(info);
					wakeUp();
				}
			}
		}
		eventStartOrResumeTime = !isEnteringSleep ? currTime : 0;
	}

	@Override
	public void run() {
		boolean resetStalledEventState = true;

		DeadlockTracker deadlockTracker = new DeadlockTracker();

		final long pollingNyquistDelay = minimumPollingDelay / 2;
		long pollingDelay = 0; // immediately updated by resetStalledEventState
		long grabStackTraceAt = 0; // immediately updated by resetStalledEventState
		long lastEventStartOrResumeTime = 0; // immediately updated by resetStalledEventState

		StackSample[] stackTraces = new StackSample[maxTraceCount];
		int numStacks = 0;

		ThreadMXBean jvmThreadManager = ManagementFactory.getThreadMXBean();
		boolean dumpLockedMonitors = jvmThreadManager.isObjectMonitorUsageSupported();
		boolean dumpLockedSynchronizers = jvmThreadManager.isSynchronizerUsageSupported();
		if (dumpAllThreads && jvmThreadManager.isThreadContentionMonitoringSupported()) {
			jvmThreadManager.setThreadContentionMonitoringEnabled(true);
		}

		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				registerDisplayListeners();
			}
		});

		long currTime = getTimestamp();

		while (!cancelled.get()) {
			long sleepFor;
			if (resetStalledEventState) {
				long eventTime = eventStartOrResumeTime;
				deadlockTracker.reset(eventTime);
				if (eventTime == 0) {
					eventTime = currTime;
				}
				grabStackTraceAt = eventTime + samplingThreshold;
				numStacks = 0;
				pollingDelay = minimumPollingDelay;
				sleepFor = pollingNyquistDelay;
				resetStalledEventState = false;
			} else if (lastEventStartOrResumeTime == 0) {
				sleepFor = pollingNyquistDelay;
			} else {
				sleepFor = Math.min(pollingNyquistDelay, Math.max(1, grabStackTraceAt - currTime));
			}

			long sleepAt = getTimestamp();

			long awakeDuration = currTime - sleepAt;
			boolean starvedAwake = awakeDuration > (sleepFor + loggingThreshold / 2);
			sleepForMillis(sleepFor);
			currTime = getTimestamp();
			long currEventStartOrResumeTime = eventStartOrResumeTime;
			long sleepDuration = currTime - sleepAt;
			boolean starvedSleep = sleepDuration > (sleepFor + loggingThreshold / 2);
			boolean starved = starvedSleep || starvedAwake;

			if (lastEventStartOrResumeTime != currEventStartOrResumeTime || starved) {
				resetStalledEventState = true;
				if (localTraceLog != null && starved) {
					if (starvedAwake) {
						localTraceLog.trace(String.format(
								"Starvation detected! Polling loop took a significant amount of threshold: %dms", //$NON-NLS-1$
								awakeDuration));
					}

					if (starvedSleep) {
						localTraceLog.trace(String.format(
								"Starvation detected! Expected a sleep of %dms but actually slept for %dms", //$NON-NLS-1$
								sleepFor, sleepDuration));
					}
				}
			} else if (lastEventStartOrResumeTime != 0) {
				deadlockTracker.logPossibleDeadlock(currTime, stackTraces, numStacks);

				if (maxTraceCount > 0 && currTime - grabStackTraceAt > 0) {
					if (numStacks == maxTraceCount) {
						numStacks = maxTraceCount / 2;
						decimate(stackTraces, maxTraceCount, numStacks, 0);
						pollingDelay *= 2;
					}

					try {
						ThreadInfo[] rawThreadStacks = dumpAllThreads
							? jvmThreadManager.dumpAllThreads(dumpLockedMonitors, dumpLockedSynchronizers)
									: new ThreadInfo[] {
									jvmThreadManager.getThreadInfo(uiThreadId, Integer.MAX_VALUE)
							};

						ThreadInfo[] threadStacks = rawThreadStacks;
						if (dumpAllThreads) {
							int index = 0;
							threadStacks = new ThreadInfo[rawThreadStacks.length - 1];

							for (int i = 0; i < rawThreadStacks.length; i++) {
								ThreadInfo currentThread = rawThreadStacks[i];

								if (!isCurrentThread(currentThread.getThreadId())) {
									if (currentThread.getThreadId() == uiThreadId && i > 0) {
										currentThread = threadStacks[0];
										threadStacks[0] = rawThreadStacks[i];
									}
									threadStacks[index++] = currentThread;
								}
							}
						}

						stackTraces[numStacks++] = new StackSample(getTimestamp(), threadStacks);
						grabStackTraceAt += pollingDelay;
					} catch (SWTException e) {
						cancelled.set(true);
						resetStalledEventState = true;
					}
				}
			}

			LongEventInfo eventSnapshot = publishEvent.getAndSet(null);
			if (starved || eventSnapshot != null) {
				if (eventSnapshot != null) {
					int trimLast = 0;
					if (numStacks - 1 > loggedTraceCount) {
						long eventEnd = eventSnapshot.start + eventSnapshot.duration;
						if (eventEnd - stackTraces[numStacks - 1].getTimestamp() < minimumPollingDelay) {
							trimLast = 1;
						}
					}

					if (numStacks > loggedTraceCount) {
						decimate(stackTraces, numStacks, loggedTraceCount, trimLast);
						numStacks = loggedTraceCount;
					}

					logEvent(new UiFreezeEvent(eventSnapshot.start, eventSnapshot.duration, stackTraces,
							numStacks, false));
				}

				resetStalledEventState = true;
				Arrays.fill(stackTraces, null);
			}

			lastEventStartOrResumeTime = currEventStartOrResumeTime;
		}
	}

	private static Display getDisplay() throws IllegalArgumentException {
		IWorkbench workbench = MonitoringPlugin.getDefault().getWorkbench();
		if (workbench == null) {
			throw new IllegalArgumentException(Messages.EventLoopMonitorThread_workbench_was_null);
		}

		Display display = workbench.getDisplay();
		if (display == null) {
			throw new IllegalArgumentException(Messages.EventLoopMonitorThread_display_was_null);
		}

		return display;
	}

	protected long getTimestamp() {
		return System.currentTimeMillis();
	}

	protected void sleepForMillis(long milliseconds) {
		if (milliseconds > 0) {
			try {
				synchronized (sleepMonitor) {
					sleepMonitor.wait(milliseconds);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	private Tracer getTracer() {
		return MonitoringPlugin.getTracer();
	}

	private void loadLoggerExtensions() {
		externalLoggers = new ArrayList<IUiFreezeEventLogger>();
		IConfigurationElement[] configElements =
				Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for (IConfigurationElement element : configElements) {
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IUiFreezeEventLogger) {
					externalLoggers.add((IUiFreezeEventLogger) object);
				} else {
					MonitoringPlugin.logWarning(String.format(
							Messages.EventLoopMonitorThread_invalid_logger_type_error_4,
							object.getClass().getName(), IUiFreezeEventLogger.class.getClass().getSimpleName(),
							EXTENSION_ID, element.getContributor().getName()));
				}
			} catch (CoreException e) {
				MonitoringPlugin.logError(e.getMessage(), e);
			}
		}
	}

	private static boolean isCurrentThread(long threadId) {
		return threadId == Thread.currentThread().getId();
	}

	private void registerDisplayListeners() {
		display.addListener(SWT.PreEvent, EventLoopMonitorThread.this);
		display.addListener(SWT.PostEvent, EventLoopMonitorThread.this);
		display.addListener(SWT.Sleep, EventLoopMonitorThread.this);
		display.addListener(SWT.Wakeup, EventLoopMonitorThread.this);
	}

	private static void decimate(Object[] list, int fromSize, int toSize, int trimTail) {
		fromSize -= trimTail;
		for (int i = 1; i < toSize; ++i) {
			int j = (i * fromSize + toSize / 2) / toSize; // == floor(i*(from/to)+0.5) == round(i*from/to)
			list[i] = list[j];
		}
	}

	private void wakeUp() {
		synchronized (sleepMonitor) {
			sleepMonitor.notify();
		}
	}

	private void logEvent(UiFreezeEvent event) {
		if (!filterHandler.shouldLogEvent(event, uiThreadId)) {
			return;
		}

		if (logLocally) {
			defaultLogger.log(event);
		}

		for (int i = 0; i < externalLoggers.size(); i++) {
			IUiFreezeEventLogger currentLogger = externalLoggers.get(i);
			try {
				currentLogger.log(event);
			} catch (Throwable t) {
				externalLoggers.remove(i);
				i--;
				MonitoringPlugin.logError(NLS.bind(
						Messages.EventLoopMonitorThread_external_exception_error_1,
						currentLogger.getClass().getName()), t);
			}
		}
	}
}
