		if (needsShowAllButton()) {
			createShowAllButton(composite);
		}
		return composite;
	}

	private boolean needsShowAllButton() {
		return activityViewerFilter.getHasEncounteredFilteredItem();
	}

	private void createShowAllButton(Composite parent) {
		showAllButton = new Button(parent, SWT.CHECK);
		showAllButton.setText(ActivityMessages.Perspective_showAll);
		showAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
			if (showAllButton.getSelection()) {
				list.resetFilters();
			} else {
				list.addFilter(activityViewerFilter);
			}
