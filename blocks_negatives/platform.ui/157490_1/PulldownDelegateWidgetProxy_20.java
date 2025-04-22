	private final Listener selectionListener = new Listener() {
		@Override
		public void handleEvent(final Event event) {
			final Widget item = event.widget;
			if (item == null) {
				return;
			}
