		final Menu menu = new Menu(dropDownBar);
		dropDownItem.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				menu.dispose();
			}
		});
		MenuItem preferencesItem = new MenuItem(menu, SWT.PUSH);
		preferencesItem.setText(UIText.CommitDialog_ConfigureLink);
		preferencesItem.addSelectionListener(new SelectionAdapter() {
