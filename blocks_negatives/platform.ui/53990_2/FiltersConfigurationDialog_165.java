		configsTable.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				configsTable.setSelection(new StructuredSelection(event.getElement()));
				updateRadioButtonsFromTable();
			}
