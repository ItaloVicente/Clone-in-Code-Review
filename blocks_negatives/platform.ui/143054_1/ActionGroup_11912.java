    /**
     * This method is called by the user of an action group to signal that the group is
     * no longer needed. Subclasses typically implement this method to deregister
     * any listeners or to free other resources.
     * <p>
     * The default implementation calls <code>setContext(null)</code>.
     * Subclasses may extend this method.
     * </p>
     */
    public void dispose() {
        setContext(null);
    }
