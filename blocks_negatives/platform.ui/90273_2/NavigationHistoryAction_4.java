    			item.addSelectionListener(new SelectionAdapter() {
    				@Override
					public void widgetSelected(SelectionEvent e) {
    					history
    					.shiftCurrentEntry(
    							(NavigationHistoryEntry) e.widget
    							.getData(), forward);
    				}
    			});
