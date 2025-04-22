			return new ThreadInfo[] { threadMXBean.getThreadInfo(uiThreadId, Integer.MAX_VALUE) };
		}
	}

	private boolean isInteresting(ThreadInfo thread) {
		for (StackTraceElement element : thread.getStackTrace()) {
			String className = element.getClassName();
			if (!className.startsWith("java.") && !className.startsWith("sun.") && //$NON-NLS-1$ //$NON-NLS-2$
					!NON_INTERESTING_THREAD_FILTER.matchesFilter(element)) {
				return true;
			}
