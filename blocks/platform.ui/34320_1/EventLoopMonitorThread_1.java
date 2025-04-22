		monitoringThreadId = Thread.currentThread().getId();
		threadMXBean = ManagementFactory.getThreadMXBean();
		dumpLockedMonitors = threadMXBean.isObjectMonitorUsageSupported();
		dumpLockedSynchronizers = threadMXBean.isSynchronizerUsageSupported();
		boolean contentionMonitoringSupported = threadMXBean.isThreadContentionMonitoringSupported();

