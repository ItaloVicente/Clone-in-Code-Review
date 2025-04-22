		if (tracer != null) {
			tracer.trace("Begin event");
		}
		if (!eventLoopIsIdle) {
			handleEventTransition(true, false);  // Log a long interval, not entering sleep.
		}
