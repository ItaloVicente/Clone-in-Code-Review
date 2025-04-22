        frames.add(frame);
        current = 0;
    }

    /**
     * Adds a property change listener.
     * Has no effect if an identical listener is already registered.
     *
     * @param listener a property change listener
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener) {
    	addListenerObject(listener);
    }

    /**
     * Moves the frame pointer back by one.
     * Has no effect if there is no frame before the current one.
     * Fires a <code>P_CURRENT_FRAME</code> property change event.
     */
    public void back() {
        if (current > 0) {
            setCurrent(current - 1);
        }
    }

    /**
     * Notifies any property change listeners that a property has changed.
     * Only listeners registered at the time this method is called are notified.
     *
     * @param event the property change event
     *
     * @see IPropertyChangeListener#propertyChange
     */
    protected void firePropertyChange(PropertyChangeEvent event) {
