        combo.addListener(SWT.DefaultSelection, new Listener() {
            @Override
			public void handleEvent(Event e) {
                setURL(combo.getText());
            }
        });
