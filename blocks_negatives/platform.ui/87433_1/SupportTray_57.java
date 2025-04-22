		container.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				destroyImages();
			}
		});
