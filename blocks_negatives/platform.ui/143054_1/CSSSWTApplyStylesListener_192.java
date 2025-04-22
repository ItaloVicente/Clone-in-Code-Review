		display.addListener(SWT.Skin, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (engine != null) {
					engine.applyStyles(event.widget, false);
				}
