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
