            IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {

        toolBarManager.add(categoriesAction);
        toolBarManager.add(filterAction);
        toolBarManager.add(defaultsAction);

        menuManager.add(categoriesAction);
        menuManager.add(filterAction);
        menuManager.add(columnsAction);

        viewer.setStatusLineManager(statusLineManager);
    }

    /**
     * Updates the model for the viewer.
     * <p>
     * Note that this means ensuring that the model reflects the state
     * of the current viewer input.
     * </p>
     */
    public void refresh() {
        if (viewer == null) {
