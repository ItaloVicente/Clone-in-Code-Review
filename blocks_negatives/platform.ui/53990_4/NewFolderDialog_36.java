		folderNameField.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				validateLinkedResource();
			}
		});
