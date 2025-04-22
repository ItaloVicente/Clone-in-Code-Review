	private void openMenuFor(ToolBar toolBar) {
		Menu menu = new Menu(toolBar);

		if (toolBar.isVisible()) {
		}

		new MenuItem(menu, SWT.SEPARATOR);
		addShowTextItem(menu);

		Rectangle bounds = toolBar.getBounds();
		Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
		menu.setLocation(point.x, point.y);
		menu.setVisible(true);
		menu.addMenuListener(menuHiddenAdapter(e -> toolBar.getDisplay().asyncExec(menu::dispose)));
	}

	private void addShowTextItem(Menu menu) {
		MenuItem showtextMenuItem = new MenuItem(menu, SWT.CHECK);
		showtextMenuItem.setText(WorkbenchMessages.PerspectiveBar_showText);
		IPreferenceStore apiPreferenceStore = PrefUtil.getAPIPreferenceStore();
		showtextMenuItem.addSelectionListener(widgetSelectedAdapter(e -> {
			boolean preference = showtextMenuItem.getSelection();
			apiPreferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_QUICK_ACCESS, preference);
		}));
		showtextMenuItem
				.setSelection(apiPreferenceStore.getBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_QUICK_ACCESS));
	}

