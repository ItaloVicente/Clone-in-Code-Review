        Listener listener = new Listener() {
            @Override
			public void handleEvent(Event event) {
                setDialogComplete(validatePage());
            }
        };
