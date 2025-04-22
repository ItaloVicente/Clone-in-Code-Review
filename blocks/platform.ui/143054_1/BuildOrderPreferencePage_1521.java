				throw new UnsupportedOperationException();
			}
		};
		maxItersField.setValidRange(1, Integer.MAX_VALUE);
		maxItersField.setPage(this);
		maxItersField.setPreferenceStore(getPreferenceStore());
		maxItersField.setPropertyChangeListener(validityChangeListener);
		maxItersField.load();
	}

	private void defaultsButtonSelected(boolean selected) {
		if (selected) {
			setBuildOrderWidgetsEnablement(false);
			customBuildOrder = buildList.getItems();
			buildList.setItems(getDefaultProjectOrder());

		} else {
			setBuildOrderWidgetsEnablement(true);
			String[] buildOrder = getCurrentBuildOrder();
			if (buildOrder == null) {
