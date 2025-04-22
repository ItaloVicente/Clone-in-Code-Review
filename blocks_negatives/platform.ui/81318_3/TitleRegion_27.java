		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (dragImage != null) {
					dragImage.dispose();
					dragImage = null;
				}
