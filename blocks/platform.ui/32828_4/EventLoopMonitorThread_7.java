		if (tracer != null) {
			tracer.trace("End event");
		}
		if (!eventLoopIsIdle) {
			handleEventTransition(true, false);  // Log a long interval, not entering sleep.
		}
