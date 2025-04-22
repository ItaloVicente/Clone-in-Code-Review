		Listener closeListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.doit = close();
			}
		};
