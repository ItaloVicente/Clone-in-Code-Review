		Event event = new Event();
		event.type = SWT.KeyDown;
		event.keyCode = SWT.CTRL;
		shell.notifyListeners(SWT.KeyDown, event);

		event = new Event();
		event.type = SWT.KeyDown;
		event.stateMask = SWT.CTRL;
		event.keyCode = 'A';
		shell.notifyListeners(SWT.KeyDown, event);
