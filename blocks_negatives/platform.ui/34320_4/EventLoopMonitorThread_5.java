		ThreadMXBean jvmThreadManager = ManagementFactory.getThreadMXBean();
		boolean dumpLockedMonitors = jvmThreadManager.isObjectMonitorUsageSupported();
		boolean dumpLockedSynchronizers = jvmThreadManager.isSynchronizerUsageSupported();
		if (dumpAllThreads && jvmThreadManager.isThreadContentionMonitoringSupported()) {
			jvmThreadManager.setThreadContentionMonitoringEnabled(true);
		}
