		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				setLocationForSelection();
				applyValidationResult(checkValid(), false);
			}
