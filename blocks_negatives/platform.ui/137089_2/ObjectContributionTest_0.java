		contextMenu.notifyListeners(SWT.Show, showEvent);

		Event hideEvent = new Event();
		hideEvent.widget = contextMenu;
		hideEvent.type = SWT.Hide;

		contextMenu.notifyListeners(SWT.Hide, hideEvent);
