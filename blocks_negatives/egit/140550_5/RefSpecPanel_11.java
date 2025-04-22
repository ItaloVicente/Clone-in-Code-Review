		addRefSpecTableListener(new SelectionChangeListener() {
			@Override
			public void selectionChanged() {
				updateForceUpdateAllButton();
				updateRemoveAllSpecButton();
			}
