		rawTable.addListener(SWT.PaintItem, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.index == 1) {
					doPaint(event);
				}
