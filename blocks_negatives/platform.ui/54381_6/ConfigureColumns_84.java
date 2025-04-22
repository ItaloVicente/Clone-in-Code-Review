				downButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event event) {
						handleMove(table, false);
					}
				});
