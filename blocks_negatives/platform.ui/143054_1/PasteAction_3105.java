    /**
     * Returns the actual target of the paste action. Returns null
     * if no valid target is selected.
     *
     * @return the actual target of the paste action
     */
    private IResource getTarget() {
        List selectedResources = getSelectedResources();
