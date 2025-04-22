                throw new UnsupportedOperationException();
            }
        };
        maxItersField.setValidRange(1, Integer.MAX_VALUE);
        maxItersField.setPage(this);
        maxItersField.setPreferenceStore(getPreferenceStore());
        maxItersField.setPropertyChangeListener(validityChangeListener);
        maxItersField.load();
    }

    /**
     * The defaults button has been selected - update the other widgets as required.
     * @param selected - whether or not the defaults button got selected
     */
    private void defaultsButtonSelected(boolean selected) {
        if (selected) {
            setBuildOrderWidgetsEnablement(false);
            customBuildOrder = buildList.getItems();
            buildList.setItems(getDefaultProjectOrder());

        } else {
            setBuildOrderWidgetsEnablement(true);
            String[] buildOrder = getCurrentBuildOrder();
            if (buildOrder == null) {
