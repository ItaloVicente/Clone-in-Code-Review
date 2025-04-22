		configsTable.addSelectionChangedListener(event -> {
			storeConfiguration();
			MarkerFieldFilterGroup group = getSelectionFromTable();
			if (group == null) {
				setFieldsEnabled(false);
			} else {
				setFieldsEnabled(true);
