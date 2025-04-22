		rawTable.addListener(SWT.EraseItem, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.index == 1) {
					event.detail &= ~SWT.FOREGROUND;
				}
