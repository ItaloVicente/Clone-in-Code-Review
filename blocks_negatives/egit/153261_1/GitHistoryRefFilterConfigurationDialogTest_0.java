	private Event createEvent(Control control) {
		Event event = new Event();
		event.keyCode = SWT.NONE;
		event.stateMask = SWT.NONE;
		event.doit = true;
		event.widget = control;
		event.button = 1;
		return event;
	}

	private void keyEvent(Control control, int keyCode) {
		Event event = createEvent(control);
		event.button = 0;
		event.type = SWT.KeyDown;
		event.keyCode = keyCode;
		event.character = (char) keyCode;

		Display.getDefault().post(event);

		event.type = SWT.KeyUp;

		Display.getDefault().post(event);
	}

	private void typeTextAndEnter(String text) {
		Control c = dialog.getShell();
		for (int i = 0; i < text.length(); i++) {
			keyEvent(c, text.charAt(i));
		}
		keyEvent(c, 13);
