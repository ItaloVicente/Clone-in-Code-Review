		final Menu menuBar = manager.createContextMenu(shell);
		Event e = new Event();
		e.type = SWT.Show;
		e.widget = menuBar;
		menuBar.notifyListeners(SWT.Show, e);
		
		MenuItem[] menuItems = menuBar.getItems();
