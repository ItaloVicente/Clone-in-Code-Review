        showAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
		    if (showAllButton.getSelection()) {
		        list.resetFilters();
		    } else {
		        list.addFilter(activityViewerFilter);
		    }
		}));
