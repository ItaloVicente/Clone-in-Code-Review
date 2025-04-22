		completionsTable.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				executeKeyBinding(event);
			}
		});
