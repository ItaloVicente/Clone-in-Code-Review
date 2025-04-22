        Listener listener = new Listener() {
            @Override
			public void handleEvent(Event event) {
                switch (event.type) {
                case SWT.Selection:
                    if (menuItem.getSelection()) {
						openEditor(descriptor, false);
					}
                    break;
                }
            }
        };
