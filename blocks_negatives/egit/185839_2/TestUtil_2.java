	/**
	 * Process all queued UI events. If called from background thread, blocks
	 * until all pending events are processed in UI thread.
	 */
	public static void processUIEvents() {
		processUIEvents(0);
	}

	/**
	 * Process all queued UI events. If called from background thread, blocks
	 * until all pending events are processed in UI thread.
	 *
	 * @param timeInMillis
	 *            time to wait. During this time all UI events are processed but
	 *            the current thread is blocked
	 */
	public static void processUIEvents(final long timeInMillis) {
	}

