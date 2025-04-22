		restoreItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				minimizedElement.getTags().remove(IPresentationEngine.MINIMIZED);
			}
		});
