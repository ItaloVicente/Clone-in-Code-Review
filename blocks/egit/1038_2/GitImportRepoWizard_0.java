	
	private void pressAltAndChar(final SWTBotShell shell, final char charToPress) {
		final Display display = Display.getDefault();
		display.syncExec(new Runnable() {
			public void run() {
				Event evt = new Event();
				evt.type = SWT.KeyDown;
				evt.item = shell.widget;
				evt.keyCode = SWT.ALT;
				display.post(evt);
				evt.keyCode = 0;
				evt.character = charToPress;
				display.post(evt);
				evt.type = SWT.KeyUp;
				display.post(evt);
				evt.keyCode = SWT.ALT;
				evt.character = ' ';
				display.post(evt);
			}
		});
	}
