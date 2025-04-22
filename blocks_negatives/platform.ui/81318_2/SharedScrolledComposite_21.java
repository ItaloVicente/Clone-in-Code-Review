		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (!ignoreResizes) {
					scheduleReflow(false);
				}
