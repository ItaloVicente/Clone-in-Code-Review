	@Override
	public void handleEvent(Event event) {
		/*
		 * Freeze monitoring involves seeing long intervals between BeginEvent/EndEvent messages,
		 * regardless of the level of event nesting. For example:
		 * 1) Log if a top-level or nested dispatch takes too long (interval is between BeginEvent
		 *    and EndEvent).
		 * 2) Log if preparation before popping up a dialog takes too long (interval is between two
		 *    BeginEvent messages).
		 * 3) Log if processing after dismissing a dialog takes too long (interval is between two
		 *    EndEvent messages).
		 * 4) Log if there is a long delay between nested calls (interval is between EndEvent and
		 *    BeginEvent). This could happen after a dialog is dismissed, does too much processing
		 *    on the UI thread, and then pops up a notification dialog.
		 * 5) Don't log for long delays between top-level events (interval is between EndEvent and
		 *    BeginEvent at the top level), which should involve sleeping.
		 *
		 * Calls to Display.sleep() make the UI responsive, whether or not events are actually
		 * dispatched, so items 1-4 above assume that there are no intervening calls to sleep()
		 * between the event transitions. Treating the BeginSleep event as an event transition lets
		 * us accurately capture true freeze intervals.
		 *
		 * Correct management of BeginSleep/EndSleep events allow us to handle items 4 and 5 above
		 * since we can tell if a long delay between an EndEvent and a BeginEvent are due to an idle
		 * state (in Display.sleep()) or a UI freeze.
		 *
		 * Since an idle system can potentially sleep for a long time, we need to avoid logging long
		 * delays that are due to sleeps. The eventStartOrResumeTime variable is set to zero
		 * when the thread is sleeping so that deadlock logging can be avoided for this case.
		 */
		switch (event.type) {
		case SWT.PreEvent:
			beginEvent();
			break;
		case SWT.PostEvent:
			endEvent();
			break;
		case SWT.Sleep:
			beginSleep();
			break;
		case SWT.Wakeup:
			endSleep();
			break;
		default:
		}
	}

	public void beginEvent() {
		if (tracer != null) {
			tracer.trace("Begin event");
		}
		handleEventTransition(true, false);  // Log a long interval, not entering sleep.
	}

	public void endEvent() {
		if (tracer != null) {
			tracer.trace("End event");
		}
		handleEventTransition(true, false);  // Log a long interval, not entering sleep.
	}

	public void beginSleep() {
		if (tracer != null) {
			tracer.trace("Begin sleep");
		}
		handleEventTransition(true, true);  // Log a long interval, entering sleep.
	}

	public void endSleep() {
		if (tracer != null) {
			tracer.trace("End sleep");
		}
		handleEventTransition(false, false);  // Don't log a long interval, not entering sleep.
