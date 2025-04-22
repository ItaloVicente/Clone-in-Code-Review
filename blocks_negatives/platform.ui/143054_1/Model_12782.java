    /**
     * Stops this model from sending change events from the given listener.
     *
     * @param toRemove
     */
    public void removeChangeListener(IChangeListener changeListener) {
        views.remove(changeListener);
    }
