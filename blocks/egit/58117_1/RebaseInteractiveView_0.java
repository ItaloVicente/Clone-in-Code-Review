		planTree.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				refreshUI();
			}
		});

