    /**
     * Returns the current structured selection in the workbench, or an empty
     * selection if nothing is selected or if selection does not include
     * objects (for example, raw text).
     *
     * @return the current structured selection in the workbench
     */
    public IStructuredSelection getStructuredSelection() {
        return selection;
    }
