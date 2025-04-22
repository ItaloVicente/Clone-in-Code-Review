		addListener(SWT.MouseExit, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ImageBasedFrame frame = (ImageBasedFrame) event.widget;
				frame.setCursor(null);
			}
