			return new ThreadInfo[] { threadMXBean.getThreadInfo(uiThreadId, Integer.MAX_VALUE) };
		}
	}

	private boolean isInteresting(ThreadInfo thread) {
		for (StackTraceElement element : thread.getStackTrace()) {
			if (!NON_INTERESTING_THREAD_FILTER.matchesFilter(element)) {
				return true;
			}
