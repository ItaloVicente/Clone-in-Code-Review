        addListener(SWT.Resize, new Listener() {
            @Override
			public void handleEvent(Event e) {
                reflow(true);
            }
        });
