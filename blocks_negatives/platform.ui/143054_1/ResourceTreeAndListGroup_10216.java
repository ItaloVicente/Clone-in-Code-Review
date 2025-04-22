    }

    /**
     *	Remove the passed listener from self's collection of clients
     *	that listen for changes to element checked states
     *
     *	@param listener ICheckStateListener
     */
    public void removeCheckStateListener(ICheckStateListener listener) {
        removeListenerObject(listener);
    }

    /**
     * Select or de-select all of the elements in the tree depending on the value of the selection
     * boolean. Be sure to update the displayed files as well.
     * @param selection
     */
    public void setAllSelections(final boolean selection) {
        if (root == null) {
