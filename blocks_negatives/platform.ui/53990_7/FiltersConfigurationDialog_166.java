		configsTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				storeConfiguration();
				MarkerFieldFilterGroup group = getSelectionFromTable();
				if (group == null) {
					setFieldsEnabled(false);
				} else {
					setFieldsEnabled(true);
				}
				updateButtonEnablement(group);
				updateConfigDesc(group);
				selectedFilterGroup = group;
