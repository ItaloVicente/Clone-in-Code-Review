		final Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!(event.widget instanceof ImageBasedFrame)) {
					return;
				}
