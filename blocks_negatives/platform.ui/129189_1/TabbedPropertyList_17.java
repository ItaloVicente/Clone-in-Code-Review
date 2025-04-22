		addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (isFocusControl()) {
					accessible.setFocus(ACC.CHILDID_SELF);
				}
			}
		});

		addListener(SWT.FocusIn, new Listener() {

			@Override
			public void handleEvent(Event event) {
