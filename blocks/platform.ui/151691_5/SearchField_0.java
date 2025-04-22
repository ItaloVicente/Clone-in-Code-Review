	private void openMenuFor(ToolBar toolBar, ToolItem quickAccessToolItem) {
		Menu menu = new Menu(toolBar);

		if (toolBar.isVisible()) {

		}

		new MenuItem(menu, SWT.SEPARATOR);
		addShowTextItem(menu, quickAccessToolItem);

		Rectangle bounds = toolBar.getBounds();
		Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
		menu.setLocation(point.x, point.y);
		menu.setVisible(true);
		menu.addMenuListener(menuHiddenAdapter(e -> toolBar.getDisplay().asyncExec(menu::dispose)));
	}

	private void addShowTextItem(Menu menu, ToolItem quickAccessToolItem) {
		MenuItem showtextMenuItem = new MenuItem(menu, SWT.CHECK);
		showtextMenuItem.setText(WorkbenchMessages.PerspectiveBar_showText);
		IPreferenceStore apiPreferenceStore = PrefUtil.getAPIPreferenceStore();
		showtextMenuItem.addSelectionListener(widgetSelectedAdapter(e -> {
			boolean preference = showtextMenuItem.getSelection();
			if (preference != apiPreferenceStore
					.getDefaultBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_QUICK_ACCESS)) {
				PrefUtil.getInternalPreferenceStore().setValue(IPreferenceConstants.OVERRIDE_PRESENTATION, true);
			}
			apiPreferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_QUICK_ACCESS, preference);
			changeShowText(preference, quickAccessToolItem);
		}));
		showtextMenuItem
				.setSelection(apiPreferenceStore.getBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_QUICK_ACCESS));
	}

	private void changeShowText(boolean showText, ToolItem quickAccessToolItem) {
		if (showText) {
			try {
				quickAccessToolItem.setText(quickAccessCommand.getName());
			} catch (NotDefinedException e) {
				e.printStackTrace();
			}
		} else {
			quickAccessToolItem.setText(""); //$NON-NLS-1$
		}
	}

>>>>>>> 0dc919f WIP - Bug 551324 - Allow to hide text in Quick Access / Find Actions tool contribution
