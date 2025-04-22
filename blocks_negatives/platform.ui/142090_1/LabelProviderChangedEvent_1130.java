    /**
     * Returns the first element whose label needs to be updated,
     * or <code>null</code> if all labels need to be updated.
     *
     * @return the element whose label needs to be updated or <code>null</code>
     */
    public Object getElement() {
        if (this.elements == null || this.elements.length == 0) {
