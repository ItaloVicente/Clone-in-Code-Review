		getShell().addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				layoutForNewMessage(true);
			}
		});
