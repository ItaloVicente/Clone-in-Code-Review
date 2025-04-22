		table.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				for (Image image : images.values()) {
					image.dispose();
				}
			}
		});
