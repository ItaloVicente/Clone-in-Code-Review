    }

    /**
     * Set all of the selections in the selection group to value
     * @param value boolean
     */
    protected void setAllSelections(boolean value) {
        selectionGroup.setAllSelections(value);
    }

    /**
     * Sets the value of this page's container resource field, or stores
     * it for future use if this page's controls do not exist yet.
     *
     * @param value String
     */
    public void setContainerFieldValue(String value) {
        if (containerNameField == null) {
