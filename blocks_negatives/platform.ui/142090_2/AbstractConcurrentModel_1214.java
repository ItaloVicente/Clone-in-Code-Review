    /**
     * Returns the array of listeners for this model
     *
     * @return the array of listeners for this model
     */
    protected final IConcurrentModelListener[] getListeners() {
    	Object[] l = listeners.getListeners();
    	IConcurrentModelListener[] result = new IConcurrentModelListener[l.length];
