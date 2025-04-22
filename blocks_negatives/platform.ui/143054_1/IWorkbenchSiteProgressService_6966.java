    /**
	 * Increments the busy counter for this workbench site. This API should only
	 * be used for background work that does not use jobs. As long as there have
	 * been more calls to incrementBusy() than to decrementBusy(), the part will
	 * show a busy affordance. Each call to incrementBusy must be followed by a
	 * call to decrementBusy once the caller no longer needs the part to show
	 * the busy affordance.
