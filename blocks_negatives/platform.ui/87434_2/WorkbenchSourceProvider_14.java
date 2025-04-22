	private final Listener listener = new Listener() {
		/**
		 * Notifies all listeners that the source has changed.
		 */
		@Override
		public final void handleEvent(final Event event) {
			if (!(event.widget instanceof Shell)) {
				if (DEBUG) {
				}
				return;
			}
