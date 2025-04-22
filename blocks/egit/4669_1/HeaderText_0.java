
	private static void createContextMenu(final StyledText styledText) {
		Menu menu = new Menu(styledText);

		final MenuItem copyItem = new MenuItem(menu, SWT.PUSH);
		copyItem.setText(UIText.Header_contextMenu_copy);
		copyItem.setEnabled(false);
		copyItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.copy();
			}
		});
		styledText.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				copyItem.setEnabled(styledText.getSelectionCount() > 0);
			}
		});

		new MenuItem(menu, SWT.SEPARATOR);

		final MenuItem selectAllItem = new MenuItem(menu, SWT.PUSH);
		selectAllItem.setText(UIText.Header_contextMenu_selectAll);
		selectAllItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.selectAll();
			}
		});

		styledText.setMenu(menu);
	}

