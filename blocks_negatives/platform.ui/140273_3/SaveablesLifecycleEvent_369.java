	 * @param source
	 *            The source of the event. If an ISaveablesSource notifies
	 *            about changes to the saveables returned by
	 *            {@link ISaveablesSource#getSaveables()}, the source must be
	 *            the ISaveablesSource object.
	 * @param eventType
	 *            the event type, currently one of POST_OPEN, PRE_CLOSE,
	 *            POST_CLOSE, DIRTY_CHANGED
	 * @param saveables
	 *            The affected saveables
	 * @param force
	 *            true if the event type is PRE_CLOSE and this is a closed force
	 *            that cannot be canceled.
