		useOverlaysItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (toolControl != null) {
					toolControl.getPersistedState().put(USE_OVERLAYS_KEY,
							Boolean.toString(!useOverlays()));
				}
