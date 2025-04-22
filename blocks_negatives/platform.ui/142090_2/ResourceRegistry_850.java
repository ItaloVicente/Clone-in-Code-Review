    /**
     * Fires a <code>PropertyChangeEvent</code>.
     *
     * @param name the name of the symbolic value that is changing.
     * @param oldValue the old value.
     * @param newValue the new value.
     */
    protected void fireMappingChanged(String name, Object oldValue,
            Object newValue) {
        final Object[] myListeners = getListeners();
        if (myListeners.length > 0) {
            PropertyChangeEvent event = new PropertyChangeEvent(this, name,
                    oldValue, newValue);
            for (Object myListener : myListeners) {
                try {
                    ((IPropertyChangeListener) myListener)
                            .propertyChange(event);
                } catch (Exception e) {
                }
            }
        }
    }
