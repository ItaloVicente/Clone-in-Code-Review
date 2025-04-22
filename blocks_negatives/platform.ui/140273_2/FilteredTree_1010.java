			clearButton.getAccessible().addAccessibleListener(
				new AccessibleAdapter() {
					@Override
					public void getName(AccessibleEvent e) {
						e.result= WorkbenchMessages.FilteredTree_AccessibleListenerClearButton;
					}
