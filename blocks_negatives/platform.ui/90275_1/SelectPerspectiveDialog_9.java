        showAllButton.addSelectionListener(new SelectionAdapter() {

            @Override
			public void widgetSelected(SelectionEvent e) {
                if (showAllButton.getSelection()) {
                    list.resetFilters();
                } else {
                    list.addFilter(activityViewerFilter);
                }
            }
        });
