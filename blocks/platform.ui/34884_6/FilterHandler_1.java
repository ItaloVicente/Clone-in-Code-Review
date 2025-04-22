package org.eclipse.ui.internal.monitoring;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
import org.eclipse.ui.monitoring.PreferenceConstants;
import org.eclipse.ui.monitoring.StackSample;
import org.eclipse.ui.monitoring.UiFreezeEvent;

public class EventLoopMonitorThread extends Thread {
	private static final int EVENT_HISTORY_SIZE = 50;
	private static final String EXTENSION_ID = "org.eclipse.ui.monitoring.logger"; //$NON-NLS-1$
	private static final String NEW_LINE_AND_BULLET = "\n* "; //$NON-NLS-1$
	private static final String TRACE_EVENT_MONITOR = "/debug/event_monitor"; //$NON-NLS-1$
	private static final String TRACE_PREFIX = "Event Loop Monitor"; //$NON-NLS-1$
	private static final Tracer tracer =
			Tracer.create(TRACE_PREFIX, PreferenceConstants.PLUGIN_ID + TRACE_EVENT_MONITOR);


	public static class Parameters {
		public int longEventWarningThreshold;
		public int longEventErrorThreshold;
		public long deadlockThreshold;
		public int maxStackSamples;
		public boolean logToErrorLog;
		public String filterTraces;

		public void checkParameters() throws IllegalArgumentException {
			StringBuilder problems = new StringBuilder();
			if (longEventWarningThreshold <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_warning_threshold_error_1,
								longEventWarningThreshold));
			}
			if (longEventErrorThreshold < longEventWarningThreshold) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_error_threshold_too_low_error_2,
								longEventErrorThreshold, longEventWarningThreshold));
			}
			if (deadlockThreshold <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_deadlock_error_1,
								deadlockThreshold));
			} else if (deadlockThreshold <= longEventErrorThreshold) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_deadlock_threshold_too_low_error_2,
								deadlockThreshold, longEventErrorThreshold));
			}

			if (problems.length() != 0) {
				throw new IllegalArgumentException(
						NLS.bind(Messages.EventLoopMonitorThread_invalid_argument_error_1,
								problems.toString()));
			}
		}
	}

	private class EventLoopState implements Listener {
		private int nestingLevel;

		private int[] nestingLevelStack = new int[32];
		private int nestingLevelStackSize;

		@Override
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.PreEvent:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				nestingLevel++;
				handleEventTransition(true, true);
				break;
			case SWT.PostEvent:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				nestingLevel--;
				handleEventTransition(true, nestingLevel > 0);
				break;
			case SWT.PreExternalEventDispatch:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				saveAndResetNestingLevel();
				handleEventTransition(true, false);
				break;
			case SWT.PostExternalEventDispatch:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				restoreNestingLevel();
				handleEventTransition(false, nestingLevel > 0);
				break;
			default:
			}
		}

		private void saveAndResetNestingLevel() {
			if (nestingLevelStackSize >= nestingLevelStack.length) {
				MonitoringPlugin.logError(
						NLS.bind(Messages.EventLoopMonitorThread_max_event_loop_depth_exceeded_1,
						nestingLevelStack.length), null);
				shutdown();
			}
			nestingLevelStack[nestingLevelStackSize++] = nestingLevel;
			nestingLevel = 0;
		}

		private void restoreNestingLevel() {
			if (nestingLevelStackSize > 0) {
				nestingLevel = nestingLevelStack[--nestingLevelStackSize];
			} else {
				nestingLevel = 0;
			}
		}
	}

	private class DeadlockTracker {
		private boolean haveAlreadyLoggedPossibleDeadlock;

		private long lastActive;

		public void logPossibleDeadlock(long currTime, StackSample[] stackSamples, int numSamples) {
			long totalDuration = currTime - lastActive;

			if (!haveAlreadyLoggedPossibleDeadlock && lastActive > 0 &&
					totalDuration > deadlockThreshold &&
					filterHandler.shouldLogEvent(stackSamples, numSamples, uiThreadId)) {
				stackSamples = Arrays.copyOf(stackSamples, numSamples);
				logEvent(new UiFreezeEvent(lastActive, totalDuration,
						Arrays.copyOf(stackSamples, numSamples), true));
				haveAlreadyLoggedPossibleDeadlock = true;
				Arrays.fill(stackSamples, null);
			}
		}

		public void reset(long lastActive) {
			this.lastActive = lastActive;
			haveAlreadyLoggedPossibleDeadlock = false;
		}
	}

	private static class EventHistory {
		private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS"); //$NON-NLS-1$

		private static class EventInfo {
			long timestamp;
			int eventType;
		}

		private final EventInfo[] buffer;
		private int start; // Index of the first recorded event.
		private int size;  // Number of recorded events.

		EventHistory(int capacity) {
			buffer = new EventInfo[capacity];
			for (int i = 0; i < capacity; i++) {
				buffer[i] = new EventInfo();
			}
		}

		synchronized void recordEvent(int eventType) {
			int j = (start + size) % buffer.length;
			EventInfo event = buffer[j];
			event.timestamp = System.currentTimeMillis();
			event.eventType = eventType;
			if (size < buffer.length) {
				size++;
			} else if (++start >= buffer.length) {
				start = 0;
			}
		}

		synchronized String extractAndClear() {
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < size; i++) {
				int j = (start + i) % buffer.length;
				EventInfo eventInfo = buffer[j];
				buf.append(TIME_FORMAT.format(new Date(eventInfo.timestamp)));
				buf.append(": "); //$NON-NLS-1$
				switch (eventInfo.eventType) {
				case SWT.PreEvent:
					buf.append("PreEvent"); //$NON-NLS-1$
					break;
				case SWT.PostEvent:
					buf.append("PostEvent"); //$NON-NLS-1$
					break;
				case SWT.PreExternalEventDispatch:
					buf.append("PreExternalEventDispatch"); //$NON-NLS-1$
					break;
				case SWT.PostExternalEventDispatch:
					buf.append("PostExternalEventDispatch"); //$NON-NLS-1$
					break;
				default:
					buf.append("Event "); //$NON-NLS-1$
					buf.append(eventInfo.eventType);
				}
				buf.append('\n');
			}
			size = 0;
			return buf.toString();
		}
	}

	private final EventLoopState eventLoopState = new EventLoopState();

	private volatile long eventStartOrResumeTime;

	private final int longEventWarningThreshold;
	private final AtomicBoolean cancelled = new AtomicBoolean(false);
	private final AtomicReference<LongEventInfo> publishEvent =
			new AtomicReference<LongEventInfo>(null);

	private final List<IUiFreezeEventLogger> externalLoggers =
			new ArrayList<IUiFreezeEventLogger>();
	private DefaultUiFreezeEventLogger defaultLogger;
	private final Display display;
	private final FilterHandler filterHandler;
	private final int longEventErrorThreshold;
	private final long sampleInterval;
	private final long allThreadsSampleInterval;
	private final int maxStackSamples;
	private final int maxLoggedStackSamples;
	private final long deadlockThreshold;
	private final long uiThreadId;
	private final Object sleepMonitor;
	private final boolean logToErrorLog;
	private EventHistory eventHistory;
	private ThreadMXBean threadMXBean;
	private boolean dumpLockedMonitors;
	private boolean dumpLockedSynchronizers;
	private long monitoringThreadId;

	public EventLoopMonitorThread(Parameters args) throws IllegalArgumentException {
		super("Event Loop Monitor"); //$NON-NLS-1$

		if (tracer != null) {
			eventHistory = new EventHistory(EVENT_HISTORY_SIZE);
		}

		Assert.isNotNull(args);

		args.checkParameters();

		setDaemon(true);
		setPriority(NORM_PRIORITY + 1);
		display = getDisplay();
		uiThreadId = this.display.getThread().getId();
		longEventWarningThreshold = Math.max(args.longEventWarningThreshold, 3);
		longEventErrorThreshold = Math.max(args.longEventErrorThreshold, longEventWarningThreshold);
		maxLoggedStackSamples = Math.max(args.maxStackSamples, 0);
		maxStackSamples = 2 * maxLoggedStackSamples;
		sampleInterval = longEventWarningThreshold * 2 / 3;
		allThreadsSampleInterval = longEventErrorThreshold * 2 / 3;
		deadlockThreshold = args.deadlockThreshold;
		logToErrorLog = args.logToErrorLog;
		filterHandler = new FilterHandler(args.filterTraces);
		sleepMonitor = new Object();
	}

	public void shutdown() throws SWTException {
		cancelled.set(true);
		if (!display.isDisposed()) {
			display.removeListener(SWT.PreEvent, eventLoopState);
			display.removeListener(SWT.PostEvent, eventLoopState);
			display.removeListener(SWT.PreExternalEventDispatch, eventLoopState);
			display.removeListener(SWT.PostExternalEventDispatch, eventLoopState);
		}
		wakeUp();
	}

	final void handleEvent(Event event) {
		eventLoopState.handleEvent(event);
	}

	private void handleEventTransition(boolean attemptToLogLongDelay, boolean startEventTimer) {
		long currTime = getTimestamp();
		if (attemptToLogLongDelay) {
			long startTime = eventStartOrResumeTime;
			if (startTime != 0) {
				int duration = (int) (currTime - startTime);
				if (duration >= longEventWarningThreshold) {
					LongEventInfo info = new LongEventInfo(startTime, duration);
					publishEvent.set(info);
					wakeUp();
				}
			}
		}
		eventStartOrResumeTime = startEventTimer ? currTime : 0;
	}

	@Override
	public void run() {
		if (logToErrorLog) {
			defaultLogger = new DefaultUiFreezeEventLogger(longEventErrorThreshold);
		}

		loadLoggerExtensions();

		if (!logToErrorLog && externalLoggers.isEmpty()) {
			MonitoringPlugin.logWarning(Messages.EventLoopMonitorThread_logging_disabled_error);
		}

		monitoringThreadId = Thread.currentThread().getId();
		threadMXBean = ManagementFactory.getThreadMXBean();
		dumpLockedMonitors = threadMXBean.isObjectMonitorUsageSupported();
		dumpLockedSynchronizers = threadMXBean.isSynchronizerUsageSupported();
		boolean contentionMonitoringSupported = threadMXBean.isThreadContentionMonitoringSupported();

		boolean resetStalledEventState = true;

		DeadlockTracker deadlockTracker = new DeadlockTracker();

		final long pollingNyquistDelay = sampleInterval / 2;
		long pollingDelay = 0; // Immediately updated by resetStalledEventState.
		long grabStackSampleAt = 0; // Immediately updated by resetStalledEventState.
		long lastEventStartOrResumeTime = 0; // Immediately updated by resetStalledEventState.

		StackSample[] stackSamples = new StackSample[maxStackSamples];
		int numSamples = 0;

		boolean dumpAllThreads = false;

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
				grabStackSampleAt = eventTime + sampleInterval;
				numSamples = 0;
				if (dumpAllThreads) {
					dumpAllThreads = false;
					if (contentionMonitoringSupported) {
						threadMXBean.setThreadContentionMonitoringEnabled(false);
					}
				}
				pollingDelay = sampleInterval;
				sleepFor = pollingNyquistDelay;
				resetStalledEventState = false;
			} else if (lastEventStartOrResumeTime == 0) {
				sleepFor = pollingNyquistDelay;
			} else {
				sleepFor = Math.min(pollingNyquistDelay, Math.max(1, grabStackSampleAt - currTime));
			}

			long sleepAt = getTimestamp();

			long awakeDuration = currTime - sleepAt;
			boolean starvedAwake = awakeDuration > (sleepFor + longEventWarningThreshold / 2);
			sleepForMillis(sleepFor);
			currTime = getTimestamp();
			long currEventStartOrResumeTime = eventStartOrResumeTime;
			long sleepDuration = currTime - sleepAt;
			boolean starvedSleep = sleepDuration > (sleepFor + longEventWarningThreshold / 2);
			boolean starved = starvedSleep || starvedAwake;

			if (lastEventStartOrResumeTime != currEventStartOrResumeTime || starved) {
				resetStalledEventState = true;
				if (tracer != null && starved) {
					if (starvedAwake) {
						tracer.trace(String.format(
								"Starvation detected! Polling loop took a significant amount of threshold: %dms", //$NON-NLS-1$
								awakeDuration));
					}

					if (starvedSleep) {
						tracer.trace(String.format(
								"Starvation detected! Expected to sleep for %dms but actually slept for %dms", //$NON-NLS-1$
								sleepFor, sleepDuration));
					}
				}
			} else if (lastEventStartOrResumeTime != 0) {
				if (!dumpAllThreads && currTime >= lastEventStartOrResumeTime + allThreadsSampleInterval) {
					dumpAllThreads = true;
					if (contentionMonitoringSupported) {
						threadMXBean.setThreadContentionMonitoringEnabled(true);
					}
				}

				deadlockTracker.logPossibleDeadlock(currTime, stackSamples, numSamples);

				if (maxStackSamples > 0 && currTime > grabStackSampleAt) {
					if (numSamples == maxStackSamples) {
						numSamples = maxStackSamples / 2;
						decimate(stackSamples, maxStackSamples, numSamples);
					}

					ThreadInfo[] threadStacks = captureThreadStacks(dumpAllThreads);
					stackSamples[numSamples++] = new StackSample(getTimestamp(), threadStacks);
					if (numSamples == maxStackSamples) {
						pollingDelay *= 2; // Reduce polling frequency.
					}
					grabStackSampleAt += pollingDelay;
				}
			}

			LongEventInfo eventSnapshot = publishEvent.getAndSet(null);
			if (starved || eventSnapshot != null) {
				if (eventSnapshot != null) {
					if (numSamples > maxLoggedStackSamples) {
						long eventEnd = eventSnapshot.start + eventSnapshot.duration;
						if (eventEnd - stackSamples[numSamples - 1].getTimestamp() < sampleInterval) {
							--numSamples;
						}
					}

					if (numSamples > maxLoggedStackSamples) {
						decimate(stackSamples, numSamples, maxLoggedStackSamples);
						numSamples = maxLoggedStackSamples;
					}

					if (filterHandler.shouldLogEvent(stackSamples, numSamples, uiThreadId)) {
						logEvent(new UiFreezeEvent(eventSnapshot.start, eventSnapshot.duration,
								Arrays.copyOf(stackSamples, numSamples), false));
					}
				}

				resetStalledEventState = true;
				Arrays.fill(stackSamples, null); // Allow the stack samples to be garbage collected.
			}

			lastEventStartOrResumeTime = currEventStartOrResumeTime;
		}
	}

	private ThreadInfo[] captureThreadStacks(boolean dumpAllThreads) {
		ThreadInfo[] threadStacks;
		if (dumpAllThreads) {
			ThreadInfo[] rawThreadStacks =
					threadMXBean.dumpAllThreads(dumpLockedMonitors, dumpLockedSynchronizers);
			threadStacks = new ThreadInfo[rawThreadStacks.length - 1];
			int index = 0;

			for (int i = 0; i < rawThreadStacks.length; i++) {
				ThreadInfo thread = rawThreadStacks[i];
				long threadId = thread.getThreadId();
				if (threadId != monitoringThreadId) {
					if (threadId == uiThreadId && i != 0) {
						thread = threadStacks[0];
						threadStacks[0] = rawThreadStacks[i];
					}
					threadStacks[index++] = thread;
				}
			}
		} else {
			threadStacks =
					new ThreadInfo[] { threadMXBean.getThreadInfo(uiThreadId, Integer.MAX_VALUE) };
		}
		return threadStacks;
	}

	private static Display getDisplay() throws IllegalStateException {
		IWorkbench workbench = MonitoringPlugin.getDefault().getWorkbench();
		if (workbench == null) {
			throw new IllegalStateException(Messages.EventLoopMonitorThread_workbench_was_null);
		}

		Display display = workbench.getDisplay();
		if (display == null) {
			throw new IllegalStateException(Messages.EventLoopMonitorThread_display_was_null);
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

	private void loadLoggerExtensions() {
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
							object.getClass().getName(),
							IUiFreezeEventLogger.class.getClass().getSimpleName(),
							EXTENSION_ID, element.getContributor().getName()));
				}
			} catch (CoreException e) {
				MonitoringPlugin.logError(e.getMessage(), e);
			}
		}
	}

	private void registerDisplayListeners() {
		display.addListener(SWT.PreEvent, eventLoopState);
		display.addListener(SWT.PostEvent, eventLoopState);
		display.addListener(SWT.PreExternalEventDispatch, eventLoopState);
		display.addListener(SWT.PostExternalEventDispatch, eventLoopState);
	}

	private static void decimate(StackSample[] samples, int fromSize, int toSize) {
		for (int i = 0; i < toSize; ++i) {
			int j = ((i + 1) * fromSize - 1) / toSize;
			samples[i] = samples[j];
		}
	}

	private void wakeUp() {
		synchronized (sleepMonitor) {
			sleepMonitor.notify();
		}
	}

	private void logEvent(UiFreezeEvent event) {
		if (tracer != null) {
			tracer.trace("Logging " + event + "Prior events:\n" + eventHistory.extractAndClear()); //$NON-NLS-1$//$NON-NLS-2$
		}

		if (logToErrorLog) {
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
