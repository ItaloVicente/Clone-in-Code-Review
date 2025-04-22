		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MouseDown:
					handleMouseDown(event);
					break;
				case SWT.KeyDown:
					handleKeyDown(event);
					break;
				case SWT.Selection:
					handleSelection(event);
					break;
				case SWT.FocusIn:
					handleFocusIn(event);
					break;
				}
