		final Button showCommandGroupFilterButton = new Button(
				hideToolbarItemsComposite, SWT.CHECK);
		showCommandGroupFilterButton
				.setText(WorkbenchMessages.HideItems_turnOnActionSets);
		showCommandGroupFilterButton
				.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
