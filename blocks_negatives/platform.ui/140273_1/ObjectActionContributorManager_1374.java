    /**
     * Loads the contributors from the workbench's registry.
     */
    private void loadContributors() {
        ObjectActionContributorReader reader = new ObjectActionContributorReader();
        reader.readPopupContributors(this);
    }
