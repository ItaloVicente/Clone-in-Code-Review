    }

    /**
     * Sets whether this page is valid.
     * The enable state of the container buttons and the
     * apply button is updated when a page's valid state
     * changes.
     * <p>
     *
     * @param b the new valid state
     */
    public void setValid(boolean b) {
        boolean oldValue = isValid;
        isValid = b;
        if (oldValue != isValid) {
            if (getContainer() != null) {
