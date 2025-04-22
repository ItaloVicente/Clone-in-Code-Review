    }

    /**
     * Sets the message to be displayed if the list is empty.
     * @param message the message to be displayed.
     */
    public void setEmptyListMessage(String message) {
        fEmptyListMessage = message;
    }

    /**
     * Specifies if multiple selection is allowed.
     * @param allowMultiple
     */
    public void setAllowMultiple(boolean allowMultiple) {
        fAllowMultiple = allowMultiple;
    }

    /**
     * Specifies if default selected events (double click) are created.
     * @param doubleClickSelects
     */
    public void setDoubleClickSelects(boolean doubleClickSelects) {
        fDoubleClickSelects = doubleClickSelects;
    }

    /**
     * Sets the sorter used by the tree viewer.
     * @param sorter
     * @deprecated as of 3.3, use {@link ElementTreeSelectionDialog#setComparator(ViewerComparator)} instead
     */
    @Deprecated
