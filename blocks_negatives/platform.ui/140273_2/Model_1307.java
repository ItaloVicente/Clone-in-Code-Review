    /**
     * Adds the given listener to the set of listeners that will be
     * notified when the state changes.
     *
     * @param toAdd
     */
    public void addChangeListener(IChangeListener changeListener) {
        changeListener.update(false);
        views.add(changeListener);
    }
