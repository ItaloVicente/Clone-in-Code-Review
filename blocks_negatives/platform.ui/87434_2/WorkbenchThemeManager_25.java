		Display.getCurrent().addListener(SWT.Settings, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateThemes();
			}
		});
