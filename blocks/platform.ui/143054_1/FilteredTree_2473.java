		filterText.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				String filterTextString = filterText.getText();
				if (filterTextString.length() == 0 || filterTextString.equals(initialText)) {
					e.result = initialText;
				} else {
					e.result = NLS.bind(WorkbenchMessages.FilteredTree_AccessibleListenerFiltered,
							new String[] { filterTextString, String.valueOf(getFilteredItemsCount()) });
				}
			}
