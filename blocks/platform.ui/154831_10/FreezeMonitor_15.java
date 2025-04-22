		monitorJob = Job.create("FreezeMonitor", (ICoreRunnable) monitor -> {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			StringBuilder result = new StringBuilder();
			result.append("Possible frozen test case\n");
			ThreadMXBean threadStuff = ManagementFactory.getThreadMXBean();
			ThreadInfo[] allThreads = threadStuff.getThreadInfo(threadStuff.getAllThreadIds(), 200);
			for (ThreadInfo threadInfo : allThreads) {
				result.append("\"");
				result.append(threadInfo.getThreadName());
				result.append("\": ");
				result.append(threadInfo.getThreadState());
				result.append("\n");
				final StackTraceElement[] elements = threadInfo.getStackTrace();
				for (StackTraceElement element : elements) {
					result.append("    ");
					result.append(element);
