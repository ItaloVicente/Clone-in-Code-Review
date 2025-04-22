		tracker.addListener(SWT.Move, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				track();
			}
		});
