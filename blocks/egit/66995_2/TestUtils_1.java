	public static String dumpThreads() {
		final StringBuilder dump = new StringBuilder();
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		final ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(
				threadMXBean.isObjectMonitorUsageSupported(),
				threadMXBean.isSynchronizerUsageSupported());
		for (ThreadInfo threadInfo : threadInfos) {
			dump.append("Thread ").append(threadInfo.getThreadId()).append(' ')
					.append(threadInfo.getThreadName()).append(' ')
					.append(threadInfo.getThreadState()).append('\n');
			LockInfo blocked = threadInfo.getLockInfo();
			if (blocked != null) {
				dump.append("  Waiting for ").append(blocked);
				String lockOwner = threadInfo.getLockOwnerName();
				if (lockOwner != null && !lockOwner.isEmpty()) {
					dump.append(" held by ").append(lockOwner).append("(id=")
							.append(threadInfo.getLockOwnerId()).append(')');
				}
				dump.append('\n');
			}
			for (LockInfo lock : threadInfo.getLockedSynchronizers()) {
				dump.append("  Holding ").append(lock).append('\n');
			}
			for (StackTraceElement s : threadInfo.getStackTrace()) {
				dump.append("  at ").append(s).append('\n');
			}
		}
		return dump.toString();
	}

