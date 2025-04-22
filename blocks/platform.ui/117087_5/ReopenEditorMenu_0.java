
		addClearHistory(menu, n);
	}

	private void addClearHistory(Menu menu, int nItems) {
		new MenuItem(menu, SWT.SEPARATOR);
		MenuItem miClear = new MenuItem(menu, SWT.PUSH);
		miClear.setText(WorkbenchMessages.OpenRecentDocumentsClear_text);
		miClear.addSelectionListener(widgetSelectedAdapter(e -> Arrays.stream(history.getItems()).forEach(item -> {
			history.remove(item);
		})));
		miClear.setEnabled(nItems > 0);
