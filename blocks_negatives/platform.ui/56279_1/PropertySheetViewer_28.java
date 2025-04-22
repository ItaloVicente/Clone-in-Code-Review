    /**
     * Sets the status line manager this view will use to show messages.
     *
     * @param manager
     *            the status line manager
     */
    public void setStatusLineManager(IStatusLineManager manager) {
        statusLineManager = manager;
    }

    /**
     * Shows the categories.
     */
    /* package */
    void showCategories() {
        isShowingCategories = true;
        refresh();
    }

    /**
     * Shows the expert properties.
     */
    /* package */
    void showExpert() {
        isShowingExpertProperties = true;
        refresh();
    }

    /**
     * Updates the categories. Reuses old categories if possible.
     */
    private void updateCategories() {
        if (categories == null) {
