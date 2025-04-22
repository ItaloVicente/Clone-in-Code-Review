	/**
	 * Returns {@code true} if given thread is the same as the current thread.
	 */
	private static boolean isCurrentThread(long threadId) {
		return threadId == Thread.currentThread().getId();
	}

