		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.stateMask == SWT.SHIFT) {
					assertEquals(
							"Incorrect key code for 'Shift+Alt+'", SWT.ALT, event.keyCode); //$NON-NLS-1$
				}
