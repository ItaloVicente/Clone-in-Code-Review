		Event showEvent = new Event();
		showEvent.widget = contextMenu;
		showEvent.type = SWT.Show;

		contextMenu.notifyListeners(SWT.Show, showEvent);

		Event hideEvent = new Event();
		hideEvent.widget = contextMenu;
		hideEvent.type = SWT.Hide;
