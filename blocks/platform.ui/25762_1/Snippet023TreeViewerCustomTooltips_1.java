	private Listener createTreeListenerFor(final TreeViewer viewer,
			final Listener labelListener) {

		return new Listener() {
			private Shell tip = null;
			private Label label = null;

			@Override
			public void handleEvent(Event event) {
