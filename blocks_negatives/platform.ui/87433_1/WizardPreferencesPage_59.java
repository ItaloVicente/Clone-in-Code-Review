			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateDescription();
			}
		});

		viewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				transferAllButton.setSelection(false);
				updateEnablement();
				updatePageCompletion();
			}
