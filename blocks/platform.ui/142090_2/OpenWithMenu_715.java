			return;
		}
		new MenuItem(menu, SWT.SEPARATOR);
		final MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
		menuItem.setText(IDEWorkbenchMessages.OpenWithMenu_Other);
		Listener listener = event -> {
			switch (event.type) {
			case SWT.Selection:
