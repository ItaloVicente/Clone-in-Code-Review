	    public void addListener(IComparatorModelListener listener) {
	        fListeners.addElement(listener);
	    }

	    /**
	     * Fires a model changed event to all listeners.
	     */
	    public void fireModelChanged(ComparatorModelChange change) {
