	/*
	 * Tracks when the current event was started, or if the event has nested {@link Event#sendEvent}
	 * calls, then the time when the most recent nested call returns and the current event is
	 * resumed.
	 *
	 * Accessed by both the UI and monitoring threads. Updated by the UI thread and read by the
	 * polling thread. Changing this in the UI thread causes the polling thread to reset its stalled
	 * event state. The UI thread sets this value to zero to indicate a sleep state and to
	 * a positive value to represent a dispatched state. (Using zero as an invalid event start time
	 * will be wrong for a 1 millisecond window when the 32-bit system clock rolls over in 2038,
	 * but we can live with skipping any events that fall in that window).
