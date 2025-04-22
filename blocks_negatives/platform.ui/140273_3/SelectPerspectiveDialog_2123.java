        if (needsShowAllButton()) {
            createShowAllButton(composite);
        }
        return composite;
    }

    /**
     * @return whether a show-all button is needed.  A show all button is needed only if the list contains filtered items.
     */
    private boolean needsShowAllButton() {
        return activityViewerFilter.getHasEncounteredFilteredItem();
    }

    /**
     * Create a show all button in the parent.
     *
     * @param parent the parent <code>Composite</code>.
     */
    private void createShowAllButton(Composite parent) {
        showAllButton = new Button(parent, SWT.CHECK);
        showAllButton
                .setText(ActivityMessages.Perspective_showAll);
        showAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
		    if (showAllButton.getSelection()) {
		        list.resetFilters();
		    } else {
		        list.addFilter(activityViewerFilter);
		    }
