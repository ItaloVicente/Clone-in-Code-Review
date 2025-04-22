		shell.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				shellDisposed = true;
			}
		});
