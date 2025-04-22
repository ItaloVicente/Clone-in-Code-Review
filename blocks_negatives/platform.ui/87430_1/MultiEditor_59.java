        parent.addListener(SWT.Activate, new Listener() {
            @Override
			public void handleEvent(Event event) {
                if (event.type == SWT.Activate) {
					activateEditor(e);
				}
            }
        });
