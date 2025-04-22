    /**
     * If set, the checked /gray state of containers (inner nodes) is derived
     * from the checked state of its leaf nodes.
     *
     * @param containerMode
     *            The containerMode to set
     */
    public void setContainerMode(boolean containerMode) {
        fContainerMode = containerMode;
    }

    /**
     * Sets the initial selection. Convenience method.
     *
     * @param selection
     *            the initial selection.
     */
    public void setInitialSelection(Object selection) {
