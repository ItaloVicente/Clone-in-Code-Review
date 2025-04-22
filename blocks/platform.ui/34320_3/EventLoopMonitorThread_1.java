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

