		final Button showCommandGroupFilterButton = new Button(
				hideMenuItemsComposite, SWT.CHECK);
		showCommandGroupFilterButton
				.setText(WorkbenchMessages.HideItems_turnOnActionSets);
		showCommandGroupFilterButton
				.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
