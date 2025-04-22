		addListener(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (e.character == '\r') {
					activateSelectedLink();
					return;
				}
